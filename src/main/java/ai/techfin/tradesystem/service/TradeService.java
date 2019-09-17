package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

    @KafkaListener(topics = KafkaTopicConfiguration.NEW_TRADE_COMMAND)
    public void trade() {

    }
}
