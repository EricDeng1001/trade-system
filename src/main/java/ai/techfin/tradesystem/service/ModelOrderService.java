package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.domain.ModelOrder;
import ai.techfin.tradesystem.domain.enums.BrokerType;
import ai.techfin.tradesystem.service.dto.ModelOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ModelOrderService {

    private static final Logger log = LoggerFactory.getLogger(ModelOrderService.class);

    private final PriceService priceService;

    @Autowired
    public ModelOrderService(PriceService priceService) {this.priceService = priceService;}

    public ModelOrderDTO createDTO(ModelOrder modelOrder, BrokerType brokerType) {
        return new ModelOrderDTO(modelOrder, priceService.getPrice(modelOrder.getStock(), brokerType));
    }

}
