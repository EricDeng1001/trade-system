package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TradeSystemKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(TradeSystemKafkaConsumer.class);


    @KafkaListener(topics = KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC, groupId = "trade-system")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC, message);
    }

}
