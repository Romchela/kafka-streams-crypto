package ru.romchela.kafkastreams.crypto.serde;

import ru.romchela.kafkastreams.crypto.dto.WeightedArithmeticMean;

/**
 * Serializer/deserializer for WeightedArithmeticMean class
 */
public class WeightedArithmeticMeanSerde extends WrapperSerde<WeightedArithmeticMean> {

    public WeightedArithmeticMeanSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(WeightedArithmeticMean.class));
    }

}
