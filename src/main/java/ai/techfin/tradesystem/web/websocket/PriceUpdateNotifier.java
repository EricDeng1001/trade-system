package ai.techfin.tradesystem.web.websocket;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.dto.PriceUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PriceUpdateNotifier {

    private static final Logger log = LoggerFactory.getLogger(PriceUpdateNotifier.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    public PriceUpdateNotifier(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(topics = KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC, id = "ws")
    public void updatePrice(String data) {
        log.info("going to push price-change message to user");
        simpMessagingTemplate.convertAndSend("/topic/price-change", PriceUpdateDTO.fromString(data));
    }
}
