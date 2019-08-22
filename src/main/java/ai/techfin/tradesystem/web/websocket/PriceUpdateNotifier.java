package ai.techfin.tradesystem.web.websocket;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.MarketType;
import ai.techfin.tradesystem.web.websocket.dto.PriceUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
public class PriceUpdateNotifier {

    private static final Logger log = LoggerFactory.getLogger(PriceUpdateNotifier.class);

    @SendTo("/topic/price-change")
    @KafkaListener(topics = {KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC}, groupId = "trade-system")
    public PriceUpdateDTO updatePrice(PriceUpdateDTO data) {
        return data;
    }

}
