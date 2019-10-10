package ai.techfin.tradesystem.service.impl;

import ai.techfin.tradesystem.config.ApplicationConstants;
import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.PriceType;
import ai.techfin.tradesystem.service.BrokerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

@Service(ApplicationConstants.BrokerService.INTERNAL)
public class InternalBroker implements BrokerService {

    @Override
    public boolean buy(String user, Long placementId, Stock stock, Long quantity, BigDecimal price,
                       PriceType priceType) {
        return false;
    }

    @Override
    public boolean sell(String user, Long placementId, Stock stock, Long quantity, BigDecimal price,
                        PriceType priceType) {
        return false;
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public boolean loginUser(String user, String password, Map<String, String> additional) {
        return false;
    }

    @Override
    public boolean subscribePrice(Stock stock) {
        return false;
    }

}
