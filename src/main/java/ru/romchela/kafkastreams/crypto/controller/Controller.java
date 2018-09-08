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

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private KafkaStreams kafkaStreams;

    @Autowired
    private ApplicationProperties properties;

    @GetMapping("/{product}/price")
    public String getPrice(@PathVariable String product) {
        final ReadOnlyKeyValueStore<String, String> prices = kafkaStreams.store(
            properties.getResultStoreName(), QueryableStoreTypes.<String,String>keyValueStore());

        return prices.get(product);
    }
}
