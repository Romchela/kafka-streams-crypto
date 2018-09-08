package ru.romchela.kafkastreams.crypto.serde;

import com.google.gson.Gson;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

public class JsonSerializer<T> implements Serializer<T> {

    private Gson gson = new Gson();

    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String topic, T data) {
        return gson.toJson(data).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public void close() {

    }
}