package main.serialize;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * created by Joshua.H.Brooks on 2020.10月.23.08.23
 */
public class UserDefinedDeSerializer implements Deserializer<Object> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        System.out.println("configuring De");
    }


    @Override
    public void close() {
        System.out.println("closing De");
    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        return SerializationUtils.deserialize(data);
    }
}
