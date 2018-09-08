package ru.romchela.kafkastreams.crypto.serde;

import com.google.gson.Gson;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

public class JsonDeserializer<T> implements Deserializer<T> {

    private Gson gson = new Gson();
    private Class<T> deserializerClass;

    public JsonDeserializer() {
    }

    public JsonDeserializer(Class<T> deserializerClass) {
        this.deserializerClass = deserializerClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(Map<String, ?> map, boolean b) {
        if (deserializerClass == null) {
            deserializerClass = (Class<T>) map.get("serializedClass");
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        return gson.fromJson(new String(data), deserializerClass);
    }

    @Override
    public void close() {
    }
}