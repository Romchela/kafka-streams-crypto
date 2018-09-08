package ru.romchela.kafkastreams.crypto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.romchela.kafkastreams.crypto.configuration.ApplicationProperties;
import ru.romchela.kafkastreams.crypto.configuration.SourcesProperties;
import ru.romchela.kafkastreams.crypto.dto.BrokerMessage;
import ru.romchela.kafkastreams.crypto.dto.SourceWeight;
import ru.romchela.kafkastreams.crypto.dto.WeightedArithmeticMean;
import ru.romchela.kafkastreams.crypto.serde.BrokerMessageSerde;
import ru.romchela.kafkastreams.crypto.serde.WeightedArithmeticMeanSerde;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class, SourcesProperties.class})
public class CryptoApplication {

    @Bean
    public Topology kStreamsTopology(final ApplicationProperties properties,
                                     final SourcesProperties sourcesProperties) {

        // Build map to get quick access to weight by product name.
        final Map<String, Double> weightBySource = sourcesProperties.getSources().stream()
            .collect(Collectors.toMap(SourceWeight::getName, SourceWeight::getWeight));

        final StreamsBuilder builder = new StreamsBuilder();
        final Consumed<Integer, BrokerMessage> consumed = Consumed.with(Serdes.Integer(), new BrokerMessageSerde());

        // Build input kafka stream. It's important to have key-value elements, so we have dummy integer key.
        final KStream<Integer, BrokerMessage> inputKStream = builder.stream(properties.getKafkaTopic(), consumed);

        // Build grouped stream by product name.
        final KGroupedStream<String, BrokerMessage> group = inputKStream
            .map((key, value) -> KeyValue.pair(value.getProduct(), value))
            .groupByKey(Serialized.with(Serdes.String(), new BrokerMessageSerde()));

        final KTable<String, WeightedArithmeticMean> weightedArithmeticMeanKTable = group.aggregate(
            WeightedArithmeticMean::new,
            (key, value, aggregation) -> {
                // Main function which calculates weighted arithmetic mean.
                // We have current price for the product in 'aggregation' variable and new price in 'value'.
                // And we combine these values here to get actual weighted arithmetic mean.
                final BigDecimal numerator = aggregation.getNumerator();
                final BigDecimal denominator = aggregation.getDenominator();
                final BigDecimal currentPrice = BigDecimal.valueOf(value.getPrice());

                final double weight = weightBySource.getOrDefault(value.getSource(), 0.0);
                final BigDecimal bigDecimalWeight = BigDecimal.valueOf(weight);

                aggregation.setNumerator(numerator.add(bigDecimalWeight.multiply(currentPrice)));
                aggregation.setDenominator(denominator.add(bigDecimalWeight));
                return aggregation;
            },
            Materialized.with(Serdes.String(), new WeightedArithmeticMeanSerde())
        );

        // Build KTable which helps to find weighted arithmetic mean by product name.
        // We materialize it to special store and then we'll use it in CryptoController.
        final String storeName = properties.getResultStoreName();
        weightedArithmeticMeanKTable.mapValues(
            v -> v.getNumerator().add(v.getDenominator()).doubleValue(),
            Materialized.<String, Double, KeyValueStore<Bytes, byte[]>>as(storeName)
                .withKeySerde(Serdes.String())
                .withValueSerde(Serdes.Double()));

        return builder.build();
    }

    @Bean
    public KafkaStreams kafkaStreams(final ApplicationProperties applicationProperties,
                                     final SourcesProperties sourcesProperties) {

        final Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationProperties.getApplicationId());
        properties.put(StreamsConfig.CLIENT_ID_CONFIG, applicationProperties.getClientId());
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getBootstrapServer());
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, BrokerMessageSerde.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, applicationProperties.getAutoOffsetReset());
        properties.put(StreamsConfig.STATE_DIR_CONFIG, applicationProperties.getStateDir());
        properties.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, applicationProperties.getCommitInterval());
        return new KafkaStreams(kStreamsTopology(applicationProperties, sourcesProperties), properties);
    }

    @Component
    public static class KafkaStreamsBootstrap implements CommandLineRunner {

        @Autowired
        private KafkaStreams kafkaStreams;

        @Override
        public void run(final String... args) {
            kafkaStreams.cleanUp();
            kafkaStreams.start();
            Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        }
    }

    public static void main(final String[] args) {
        SpringApplication.run(CryptoApplication.class);
    }

}
