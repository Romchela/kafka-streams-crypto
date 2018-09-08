package ru.romchela.kafkastreams.crypto.serde;

import com.google.gson.Gson;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

/**
 * Common json serializer
 * @param <T> class for serialization
 */
public class JsonSerializer<T> implements Serializer<T> {

    private Gson gson = new Gson();

    public void configure(final Map<String, ?> map, final boolean b) {
    }

    @Override
    public byte[] serialize(final String topic, final T data) {
        return gson.toJson(data).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public void close() {

    }
}