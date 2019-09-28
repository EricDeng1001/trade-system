package ai.techfin.tradesystem.config;

import ai.techfin.tradesystem.service.util.BeanUtils;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class KafkaObjectDeserializer implements Deserializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        return BeanUtils.byteToObject(bytes);
    }

    @Override
    public void close() {

    }
}
