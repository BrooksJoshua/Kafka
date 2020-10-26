package main.serialize;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;
import java.util.Map;

/**
 * created by Joshua.H.Brooks on 2020.10月.23.08.21
 */
public class UserDefinedSerializer implements Serializer<Object> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        System.out.println("configured");
    }

    @Override
    public void close() {
        System.out.println("closed");
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return SerializationUtils.serialize((Serializable) data);
    }
}
