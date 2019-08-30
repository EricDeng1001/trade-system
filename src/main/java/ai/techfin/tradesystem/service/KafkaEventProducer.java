package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Producing message to {} : {}", KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC, message);
        this.kafkaTemplate.send(KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC, message);
    }

}
