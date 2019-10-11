package ai.techfin.tradesystem.service.impl;

import ai.techfin.tradesystem.config.ApplicationConstants;
import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.BrokerType;
import ai.techfin.tradesystem.domain.enums.PriceType;
import ai.techfin.tradesystem.service.BrokerService;
import ai.techfin.tradesystem.service.dto.PriceUpdateDTO;
import ai.techfin.xtpms.service.broker.dto.TradeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service(ApplicationConstants.BrokerService.INTERNAL)
public class InternalBroker implements BrokerService {

    private final KafkaTemplate<String, TradeResponseDTO> tradeResponseDTOKafkaTemplate;

    private final KafkaTemplate<String, PriceUpdateDTO> priceUpdateDTOKafkaTemplate;

    private final List<Stock> stocksToSendPrice = new LinkedList<>();

    @Autowired
    public InternalBroker(
        KafkaTemplate<String, TradeResponseDTO> tradeResponseDTOKafkaTemplate,
        KafkaTemplate<String, PriceUpdateDTO> priceUpdateDTOKafkaTemplate) {
        this.tradeResponseDTOKafkaTemplate = tradeResponseDTOKafkaTemplate;
        this.priceUpdateDTOKafkaTemplate = priceUpdateDTOKafkaTemplate;}

    @Override
    public boolean buy(String user, Long placementId, Stock stock, Long quantity, BigDecimal price,
                       PriceType priceType) {
        tradeResponseDTOKafkaTemplate
            .send(KafkaTopicConfiguration.XTP_TRADE_SUCCEED, new TradeResponseDTO(placementId, quantity, price, stock));
        return true;
    }

    @Override
    public boolean sell(String user, Long placementId, Stock stock, Long quantity, BigDecimal price,
                        PriceType priceType) {
        tradeResponseDTOKafkaTemplate
            .send(KafkaTopicConfiguration.XTP_TRADE_SUCCEED, new TradeResponseDTO(placementId, quantity, price, stock));
        return true;
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public boolean loginUser(String user, String password, Map<String, String> additional) {
        return true;
    }

    @Override
    public boolean subscribePrice(Stock stock) {
        stocksToSendPrice.add(stock);
        return true;
    }

    @Scheduled(fixedRate = 1000000)
    void sendMockPrice() {
        for (var s: stocksToSendPrice) {
            priceUpdateDTOKafkaTemplate.send(KafkaTopicConfiguration.XTP_PRICE_CHANGE_TOPIC, new PriceUpdateDTO(s, BigDecimal.TEN, BrokerType.INTERNAL_SIM));
        }
    }
}
