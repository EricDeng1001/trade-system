package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.PriceType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public interface BrokerService {

    boolean buy(String user, Stock stock, BigInteger quantity, BigDecimal price, PriceType priceType);

    boolean sell(String user, Stock stock, BigInteger quantity, BigDecimal price, PriceType priceType);

    boolean init();

    boolean loginUser(String user, String password, Map<String, String> additional);

    boolean subscribePrice(Stock stock);
}
