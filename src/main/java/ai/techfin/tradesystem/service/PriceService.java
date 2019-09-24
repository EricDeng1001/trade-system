package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.MarketType;
import ai.techfin.tradesystem.service.dto.PriceUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceService {

    private static final Logger log = LoggerFactory.getLogger(PriceService.class);

    private BigDecimal price;

    @KafkaListener(topics = KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC)
    public void updatePrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
