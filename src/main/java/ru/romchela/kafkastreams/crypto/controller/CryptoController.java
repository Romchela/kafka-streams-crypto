package ru.romchela.kafkastreams.crypto.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romchela.kafkastreams.crypto.configuration.ApplicationProperties;
import ru.romchela.kafkastreams.crypto.exception.ProductNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class CryptoController {

    @Autowired
    private KafkaStreams kafkaStreams;

    @Autowired
    private ApplicationProperties properties;

    @GetMapping("/{product}/price")
    public Double getPrice(@PathVariable String product) {

        final ReadOnlyKeyValueStore<String, Double> prices = kafkaStreams.store(
            properties.getResultStoreName(), QueryableStoreTypes.<String, Double> keyValueStore());

        final Double price = prices.get(product);
        if (price == null) {
            throw new ProductNotFoundException(product);
        }
        return price;
    }
}
