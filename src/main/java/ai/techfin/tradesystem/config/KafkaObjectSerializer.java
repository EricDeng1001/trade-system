package ai.techfin.tradesystem.config;

import ai.techfin.tradesystem.service.util.BeanUtils;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class KafkaObjectSerializer implements Serializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Object o) {
        return BeanUtils.objectToByte(o);
    }

    @Override
    public void close() {

    }
}
