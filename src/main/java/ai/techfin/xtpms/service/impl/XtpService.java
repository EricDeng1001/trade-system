package ai.techfin.xtpms.service.impl;

import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.PriceType;
import ai.techfin.tradesystem.service.BrokerService;
import ai.techfin.xtpms.service.broker.utils.PriceTypeHelper;
import ai.techfin.xtpms.service.broker.utils.TickerHelper;
import ai.techfin.xtpms.adapter.AsyncQuoteApi;
import ai.techfin.xtpms.adapter.AsyncTradeApi;
import com.zts.xtp.common.enums.BusinessType;
import com.zts.xtp.common.enums.PositionEffectType;
import com.zts.xtp.common.enums.SideType;
import com.zts.xtp.trade.model.request.OrderInsertRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service("xtp-broker")
public class XtpService implements BrokerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XtpService.class);

    private static volatile boolean tradeInit = false;

    private static volatile boolean quoteInit = false;

    private final AsyncTradeApi tradeApi;

    private final AsyncQuoteApi quoteApi;

    @Autowired
    public XtpService(AsyncTradeApi tradeApi, AsyncQuoteApi quoteApi) {
        this.tradeApi = tradeApi;
        this.quoteApi = quoteApi;
    }

    @Override
    public boolean buy(String user, Long placementId, Stock stock, Long quantity, BigDecimal price, PriceType priceType) {
        try {
            if(!tradeInit && !init()){
                LOGGER.error("buy invoke failed, user:{}, reason:{}", user, "Xtp TradeApi Uninitialized");
                return false;
            }
            if (!checkRequestParam(user, placementId, stock, quantity, price, priceType, "BUY")) {
                return false;
            }
            String ticker = stock.getName();
            OrderInsertRequest req = OrderInsertRequest
                    .builder()
                    .orderXtpId("0")
                    .orderClientId(AsyncTradeApi.clientId)
                    .ticker(ticker)
                    .marketType(TickerHelper.getMarket(ticker))
                    .price(price.doubleValue())
                    .quantity(quantity)
                    .priceType(PriceTypeHelper.getPriceType(priceType.name()))
                    .sideType(SideType.XTP_SIDE_BUY)
                    .businessType(BusinessType.XTP_BUSINESS_TYPE_CASH)
                    .positionEffectType(PositionEffectType.XTP_POSITION_EFFECT_CLOSE).build();
            boolean invoke = tradeApi.sellOrBuy(req, user, placementId);
            LOGGER.info("buy invoke {} ", invoke);
            return invoke;
        } catch (Exception e) {
            LOGGER.error("buy invoke fail , reason: {} " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sell(String user, Long placementId, Stock stock, Long quantity, BigDecimal price, PriceType priceType) {
        try {
            if(!tradeInit && !init()){
                LOGGER.error("sell invoke failed, user:{}, reason:{}", user, "Xtp TradeApi Uninitialized");
                return false;
            }
            if (!checkRequestParam(user, placementId, stock, quantity, price, priceType, "SELL")) {
                return false;
            }
            String ticker = stock.getName();
            OrderInsertRequest req = OrderInsertRequest.builder()
                    .orderXtpId("0")
                    .orderClientId(AsyncTradeApi.clientId)
                    .ticker(ticker)
                    .marketType(TickerHelper.getMarket(ticker))
                    .price(price.doubleValue())
                    .quantity(quantity)
                    .priceType(PriceTypeHelper.getPriceType(priceType.name()))
                    .sideType(SideType.XTP_SIDE_SELL)
                    .businessType(BusinessType.XTP_BUSINESS_TYPE_CASH)
                    .positionEffectType(PositionEffectType.XTP_POSITION_EFFECT_CLOSE).build();
            boolean invoke = tradeApi.sellOrBuy(req, user, placementId);
            LOGGER.info("sell invoke {} ", invoke);
            return invoke;
        } catch (Exception e) {
            LOGGER.error("sell invoke failed, user:{}, reason:{}", user, e.getMessage());
            return false;
        }
    }

    /**
     * 校验传入参数
     *
     * @param user
     * @param stock
     * @param quantity
     * @param price
     * @param priceType
     * @param type
     * @return
     */
    private boolean checkRequestParam(String user, Long placementId, Stock stock, Long quantity, BigDecimal price, PriceType priceType, String type) {
        if (Optional.ofNullable(user).filter(user1 -> !StringUtils.isEmpty(user1)).isEmpty()) {
            LOGGER.error("{} error, User invalid, user : {}", type, user);
            return false;
        }
        if (Optional.ofNullable(placementId).filter(placementId1 -> placementId1 != 0L).isEmpty()) {
            LOGGER.error("{} error, PlacementId invalid, placementId : {}", type, placementId);
            return false;
        }
        if (Optional.ofNullable(stock).map(stock1 -> stock1.getName()).filter(name -> !StringUtils.isEmpty(name)).isEmpty()) {
            LOGGER.error("{} error, Stock invalid, stock.name : {} ", type, stock.getName());
            return false;
        }
        if (Optional.ofNullable(priceType).isEmpty()) {
            LOGGER.error("{} error, priceType invalid, priceType : {}", type, priceType);
            return false;
        }
        if (Optional.ofNullable(price).filter(price1 -> price1.compareTo(new BigDecimal("0")) > 0).isEmpty()) {
            LOGGER.error("{} error, Price invalid, price : {}", type, price);
            return false;
        }
        if (Optional.ofNullable(quantity).filter(quantity1 -> quantity1 != 0L).isEmpty()) {
            LOGGER.error("{} error, Quantity invalid, quantity : {}", type, quantity);
            return false;
        }
        return true;
    }

    @Override
    public boolean init() {
        if(!tradeInit && tradeApi.init()){
            tradeInit = true;
        }
        if(!quoteInit && quoteApi.init()){
            quoteInit = true;
        }
        return tradeInit && quoteInit;
    }

    @Override
    public boolean loginUser(String user, String password, Map<String, String> additional) {
        if(!tradeInit && !init()){
            LOGGER.error("loginUser invoke failed, user:{}, reason:{}", user, "Xtp TradeApi Uninitialized");
            return false;
        }
        if (StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(password)) {
            tradeApi.login(user, password);
            return true;
        } else {
            LOGGER.error("login error,user : {}, password : {}", user, password);
            return false;
        }
    }

    @Override
    public boolean subscribePrice(Stock stock) {
        if(!quoteInit && !init()){
            LOGGER.error("subscribePrice invoke failed, reason:{}",  "Xtp QuoteApi Uninitialized");
            return false;
        }
        this.quoteApi.subscribePrice(stock.getName(), TickerHelper.getMarket(stock.getName()));
        return true;
    }
}
