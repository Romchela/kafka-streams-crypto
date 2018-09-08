package ru.romchela.kafkastreams.crypto.serde;

import com.google.gson.Gson;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * Common json deserializer
 * @param <T> class for deserialization
 */
public class JsonDeserializer<T> implements Deserializer<T> {

    private final Gson gson = new Gson();
    private Class<T> deserializerClass;

    public JsonDeserializer() {
    }

    public JsonDeserializer(final Class<T> deserializerClass) {
        this.deserializerClass = deserializerClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(final Map<String, ?> map, final boolean b) {
        if (deserializerClass == null) {
            deserializerClass = (Class<T>) map.get("serializedClass");
        }
    }

    @Override
    public T deserialize(final String topic, final byte[] data) {
        if (data == null) {
            return null;
        }

        return gson.fromJson(new String(data), deserializerClass);
    }

    @Override
    public void close() {
    }
}