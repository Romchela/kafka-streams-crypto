package ru.romchela.kafkastreams.crypto.serde;

import ru.romchela.kafkastreams.crypto.dto.BrokerMessage;

/**
 * Serializer/deserializer for BrokerMessage class
 */
public class BrokerMessageSerde extends WrapperSerde<BrokerMessage> {

    public BrokerMessageSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(BrokerMessage.class));
    }

}
