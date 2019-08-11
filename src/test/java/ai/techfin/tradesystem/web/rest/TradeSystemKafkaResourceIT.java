package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.TradeSystemApp;
import ai.techfin.tradesystem.service.TradeSystemKafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EmbeddedKafka
@SpringBootTest(classes = TradeSystemApp.class)
class TradeSystemKafkaResourceIT {

    @Autowired
    private TradeSystemKafkaProducer kafkaProducer;

    private MockMvc restMockMvc;

    @BeforeEach
    void setup() {
        TradeSystemKafkaResource kafkaResource = new TradeSystemKafkaResource(kafkaProducer);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(kafkaResource)
            .build();
    }

    @Test
    void sendMessageToKafkaTopic() throws Exception {
        restMockMvc.perform(post("/api/trade-system-kafka/publish?message=yolo"))
            .andExpect(status().isOk());
    }
}
