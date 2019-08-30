package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.service.KafkaEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/trade-system-kafka")
public class KafkaWebEntry {

    private final Logger log = LoggerFactory.getLogger(KafkaWebEntry.class);

    private KafkaEventProducer kafkaProducer;

    public KafkaWebEntry(KafkaEventProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
