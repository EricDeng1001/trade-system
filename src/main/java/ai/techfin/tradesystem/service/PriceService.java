package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.service.dto.PriceUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PriceService {

    private static final Logger log = LoggerFactory.getLogger(PriceService.class);

    private ConcurrentHashMap<Stock, BigDecimal> xtpPriceMap = new ConcurrentHashMap<>(10007);

    @KafkaListener(topics = KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC)
    public void updatePrice(PriceUpdateDTO priceUpdateDTO) {
        switch (priceUpdateDTO.getBroker()) {
            case XTP:
                xtpPriceMap.put(priceUpdateDTO.getStock(), priceUpdateDTO.getPrice());
                break;
            case INTERNAL_SIM:
            case CTP:
        }
    }

    public BigDecimal getXtpPrice(Stock stock) {
        return xtpPriceMap.get(stock);
    }

}
