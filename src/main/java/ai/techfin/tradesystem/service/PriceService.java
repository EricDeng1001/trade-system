package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.MarketType;
import ai.techfin.tradesystem.web.websocket.dto.PriceUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceService {

    private static final Logger log = LoggerFactory.getLogger(PriceService.class);

    private final KafkaTemplate<String, PriceUpdateDTO> priceUpdatePublisher;

    public PriceService(KafkaTemplate<String, PriceUpdateDTO> priceUpdatePublisher) {
        this.priceUpdatePublisher = priceUpdatePublisher;
    }

    public void updatePrice() {
        priceUpdatePublisher.send(
            KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC,
            new PriceUpdateDTO(new Stock("000001", MarketType.SZ), BigDecimal.valueOf(2), "xtp"));
    }

}
