package ai.techfin.tradesystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TradeSystemKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(TradeSystemKafkaConsumer.class);
    private static final String TOPIC = "topic_tradesystem";

    @KafkaListener(topics = "topic_tradesystem", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
