package ai.techfin.tradesystem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {

    public static final String XTP_PRICE_CHANGE_TOPIC = "pp_xx_zz_yy";

    public static final String NEW_TRADE_COMMAND = "trade_trade_trade";

    public static final String XTP_TRADE_SUCCEED = "xtp-t-s";

    public static final String XTP_TRADE_FAILED = "xtp-t-f";

    public static final String XTP_ASSET_RESPONSE = "xtp-a-s";

    @Bean(XTP_PRICE_CHANGE_TOPIC)
    public NewTopic xtpPriceChange() {
        return new NewTopic(XTP_PRICE_CHANGE_TOPIC, 1, (short) 1);
    }

    @Bean(NEW_TRADE_COMMAND)
    public NewTopic newTradeCommand() { return new NewTopic(NEW_TRADE_COMMAND, 1, (short) 1);}

}
