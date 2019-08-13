package ai.techfin.tradesystem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {

    public static final String XTP_PRICE_CHANGE_TOPIC = "xtpPriceChangeTopic";

    @Bean(XTP_PRICE_CHANGE_TOPIC)
    public NewTopic xtpPriceChange() {
        return new NewTopic(XTP_PRICE_CHANGE_TOPIC, 1, (short) 1);
    }
}
