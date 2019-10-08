package ai.techfin.xtpms.service.impl;

import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.PriceType;
import ai.techfin.tradesystem.service.BrokerService;
import ai.techfin.xtpms.service.broker.utils.PriceTypeHelper;
import ai.techfin.xtpms.service.broker.utils.TickerHelper;
import ai.techfin.xtpms.xtp.async.AsyncQuoteApi;
import ai.techfin.xtpms.xtp.async.AsyncTradeApi;
import com.zts.xtp.common.enums.BusinessType;
import com.zts.xtp.common.enums.PositionEffectType;
import com.zts.xtp.common.enums.SideType;
import com.zts.xtp.trade.model.request.OrderInsertRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Service("xtp-broker")
public class XtpService implements BrokerService, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(XtpService.class);

    private static volatile boolean isInit = false;

    private ApplicationContext context;

    private AsyncTradeApi tradeApi;

    private AsyncQuoteApi quoteApi;

    public XtpService() {
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public boolean buy(String user,Long placementId, Stock stock, Long quantity, BigDecimal price, PriceType priceType) {
        try {
            checkInit(isInit);
            if (!isInit) {
                return false;
            }
            if (!checkRequestParam(user, stock, quantity, price, priceType, "BUY")) {
                return false;
            }
            String ticker = stock.getName();
            OrderInsertRequest req = OrderInsertRequest
                    .builder()
                    .orderXtpId("0")
                    .orderClientId(AsyncTradeApi.clientId)
                    .ticker(ticker)
                    .marketType(TickerHelper.getMarket(ticker))
                    .price(price.longValue())
                    .quantity(quantity)
                    .priceType(PriceTypeHelper.getPriceType(priceType.name()))
                    .sideType(SideType.XTP_SIDE_BUY)
                    .businessType(BusinessType.XTP_BUSINESS_TYPE_CASH)
                    .positionEffectType(PositionEffectType.XTP_POSITION_EFFECT_CLOSE).build();
            boolean invoke = tradeApi.sellOrBuy(req, user);
            LOGGER.info("buy invoke {} ", invoke);
            return invoke;
        } catch (Exception e) {
            LOGGER.error("buy invoke fail , reason: {} " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sell(String user,Long placementId, Stock stock, Long quantity, BigDecimal price, PriceType priceType) {
        try {
            checkInit(isInit);
            if (!isInit) {
                return false;
            }
            if (!checkRequestParam(user, stock, quantity, price, priceType, "SELL")) {
                return false;
            }
            String ticker = stock.getName();
            OrderInsertRequest req = OrderInsertRequest.builder()
                    .orderXtpId("0")
                    .orderClientId(AsyncTradeApi.clientId)
                    .ticker(ticker)
                    .marketType(TickerHelper.getMarket(ticker))
                    .price(price.longValue())
                    .quantity(quantity)
                    .priceType(PriceTypeHelper.getPriceType(priceType.name()))
                    .sideType(SideType.XTP_SIDE_SELL)
                    .businessType(BusinessType.XTP_BUSINESS_TYPE_CASH)
                    .positionEffectType(PositionEffectType.XTP_POSITION_EFFECT_CLOSE).build();
            boolean invoke = tradeApi.sellOrBuy(req, user);
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
    private boolean checkRequestParam(String user, Stock stock, Long quantity, BigDecimal price, PriceType priceType, String type) {
        if (Optional.ofNullable(user).filter(user1 -> !StringUtils.isEmpty(user1)).isEmpty()) {
            LOGGER.error("{} error, User invalid, user : {}", type, user);
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
        checkInit(isInit);
        if (!isInit) {
            return false;
        }
        return true;
    }

    @Override
    public boolean loginUser(String user, String password, Map<String, String> additional) {
        checkInit(isInit);
        if (!isInit) {
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
        checkInit(isInit);
        if (!isInit) {
            return false;
        }
        this.quoteApi.queryTickerPrice(stock.getName(), TickerHelper.getMarket(stock.getName()));
        return true;
    }

    //防止第一次init速度慢，导致类没有成功加载，调用其函数导致空指针
    @Async()
    void asyncInit(CountDownLatch countDownLatch) {
        try {
            if (!isInit) {
                isInit = true;
                this.tradeApi = (AsyncTradeApi) this.context.getBean("asyncTradeApi");
                this.quoteApi = (AsyncQuoteApi) this.context.getBean("asyncQuoteApi");
            }
        } catch (Exception e) {
            isInit = false;
            LOGGER.error("XtpService init error, reason: {}", e.getMessage());
        } finally {
            countDownLatch.countDown();
        }
    }

    private void checkInit(boolean isInit) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        if (!isInit) {
            asyncInit(countDownLatch);
        } else {
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            LOGGER.error("xtp init error, reason:{}", e.getMessage());
        }
    }
}
