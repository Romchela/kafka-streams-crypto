package ru.romchela.kafkastreams.crypto;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import ru.romchela.kafkastreams.crypto.dto.BrokerMessage;
import ru.romchela.kafkastreams.crypto.serde.JsonSerializer;

public class BrokerMessageProducer {

    public static void main(String[] a) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        //props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        //props.put("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");

        JsonSerializer<BrokerMessage> brokerMessageJsonSerializer = new JsonSerializer<>();


        Producer<Integer, BrokerMessage> producer = new KafkaProducer<>(props, new IntegerSerializer(),
                                                                        brokerMessageJsonSerializer);

        List<String> products = Arrays.asList("btc", "eth", "lite");
        for(int i = 0; i < 1; i++) {
            BrokerMessage m = new BrokerMessage();
            Random random = new Random();
            m.setPrice(7000L);
            //m.setPrice(Math.abs(random.nextLong()) % 100);
            //m.setProduct(products.get(Math.abs(random.nextInt()) % 3));
            m.setProduct(products.get(0));
            m.setSource("exmo");
            producer.send(new ProducerRecord<>("avgtopic", 1, m));
        }

        producer.close();

    }
}
