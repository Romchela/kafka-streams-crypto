package ru.romchela.kafkastreams.crypto.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romchela.kafkastreams.crypto.configuration.ApplicationProperties;

/**
 * Rest controller for getting information about crypto
 */
@RestController
@RequestMapping("/api/v1")
public class CryptoController {

    @Autowired
    private KafkaStreams kafkaStreams;

    @Autowired
    private ApplicationProperties properties;

    /**
     * Returns weighted arithmetic mean of current product
     * @param product - name of product in any case
     * @return price which is double type
     */
    @GetMapping("/{product}/price")
    public ResponseEntity<Double> getPrice(@PathVariable String product) {

        final ReadOnlyKeyValueStore<String, Double> prices;
        try {
            prices = kafkaStreams.store(
                properties.getResultStoreName(), QueryableStoreTypes.<String, Double>keyValueStore());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Double price = prices.get(product.toLowerCase());
        if (price == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(price, HttpStatus.OK);
    }
}
