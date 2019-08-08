package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.service.TradeSystemKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/trade-system-kafka")
public class TradeSystemKafkaResource {

    private final Logger log = LoggerFactory.getLogger(TradeSystemKafkaResource.class);

    private TradeSystemKafkaProducer kafkaProducer;

    public TradeSystemKafkaResource(TradeSystemKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
