package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.ApplicationConstants;
import ai.techfin.tradesystem.domain.*;
import ai.techfin.tradesystem.domain.enums.BrokerType;
import ai.techfin.tradesystem.domain.enums.PriceType;
import ai.techfin.tradesystem.domain.enums.TradeType;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.repository.PlacementListRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class TradeService {

    private final ModelOrderListRepository modelOrderListRepository;

    private final PlacementListRepository placementListRepository;

    private final BrokerServiceFactory brokerServiceFactory;
    private final PriceService priceService;

    public TradeService(
        PriceService priceService,
        ModelOrderListRepository modelOrderListRepository,
        PlacementListRepository placementListRepository,
        BrokerServiceFactory brokerServiceFactory) {
        this.brokerServiceFactory = brokerServiceFactory;
        this.priceService = priceService;
        this.modelOrderListRepository = modelOrderListRepository;
        this.placementListRepository = placementListRepository;
        for (var provider: BrokerType.values()) {
            brokerServiceFactory.getBrokerService(provider).init();
        }
    }

    public void loginProductAccount(Product product) {
        String username = null;
        String password = null;
        Map<String, String> additional = new HashMap<>();
        BrokerType provider = product.getProvider();
        BrokerService brokerService = brokerServiceFactory.getBrokerService(provider);
        switch (provider) {
            case INTERNAL_SIM:
            case CTP:
            case XTP:
                XtpAccount xtpAccount = product.getXtpAccount();
                username = xtpAccount.getAccount();
                password = xtpAccount.getPassword();
                additional.put(XtpAccount.TRADE_KEY_PROP_NAME, xtpAccount.getTradeKey());
        }
        brokerService.loginUser(username, password, additional);
    }

    public PlacementList process(Long modelOrderListId, TradeType tradeType) {
        ModelOrderList modelOrderList = modelOrderListRepository.getOne(modelOrderListId);
        Product product = modelOrderList.getProduct();
        BrokerType provider = product.getProvider();
        BrokerService brokerService = brokerServiceFactory.getBrokerService(provider);

        Set<ModelOrder> orders;
        if (tradeType == TradeType.SELL) {
            orders = modelOrderList.getSellList();
        } else {
            orders = modelOrderList.getBuyList();
        }

        String brokerUser = product.getXtpAccount().getAccount();
        BigDecimal hundred = BigDecimal.valueOf(100);
        Set<Placement> placements = new HashSet<>();
        PlacementList placementList = new PlacementList();
        placementList = placementListRepository.save(placementList);

        for (ModelOrder modelOrder : orders) {
            BigDecimal price = priceService.getPrice(modelOrder.getStock(), provider);
            Long quantity =
                modelOrder.getMoney().divide(price.multiply(hundred), 0, RoundingMode.FLOOR).multiply(hundred)
                    .longValue();
            Placement placement = new Placement(modelOrder.getStock(), quantity, price);
            placements.add(placement);
            if (tradeType == TradeType.SELL) {
                brokerService
                    .sell(brokerUser, placementList.getId(), modelOrder.getStock(), quantity, price, PriceType.LIMITED);
            } else {
                brokerService
                    .buy(brokerUser, placementList.getId(), modelOrder.getStock(), quantity, price, PriceType.LIMITED);
            }
        }
        placementList.setPlacements(placements);
        placementList.setModelOrderList(modelOrderList);
        placementListRepository.save(placementList);
        return placementList;
    }

    /**
     * subscribe the real time price in given provider
     *
     * @param stock    stock to subscribe
     * @param provider broker who provide this service
     */
    public void subscribeStockPrice(Stock stock, BrokerType provider) {
        BrokerService brokerService = brokerServiceFactory.getBrokerService(provider);
        brokerService.subscribePrice(stock);
    }

}
