package ai.techfin.xtpms.service.broker.utils;

import ai.techfin.tradesystem.domain.enums.*;
import com.zts.xtp.common.enums.ExchangeType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ClassConverterUtils {

    public PriceType convertPriceType(com.zts.xtp.common.enums.PriceType xtpPriceType) {
        switch (xtpPriceType) {
            case XTP_PRICE_LIMIT:
                return PriceType.LIMITED;
            case XTP_PRICE_BEST5_OR_LIMIT:
                return PriceType.FIVE_LEVEL;
        }
        return null;
    }

    public TradeType converterSideType(com.zts.xtp.common.enums.SideType xtpSideType) {
        switch (xtpSideType) {
            case XTP_SIDE_BUY:
                return TradeType.BUY;
            case XTP_SIDE_SELL:
                return TradeType.SELL;
        }
        return null;
    }

    public MarketType converterMarketType(com.zts.xtp.common.enums.MarketType xtpMarketType) {
        switch (xtpMarketType) {
            case XTP_MKT_SH_A:
                return MarketType.SH;
            case XTP_MKT_SZ_A:
                return MarketType.SZ;
        }
        return null;
    }

    public OrderStatusType converterOrderStatusType(com.zts.xtp.common.enums.OrderStatusType xtpOrderStatusType) {
        switch (xtpOrderStatusType) {
            case XTP_ORDER_STATUS_REJECTED:
                return OrderStatusType.REJECTED;
            case XTP_ORDER_STATUS_ALLTRADED:
                return OrderStatusType.ALLTRADED;
            case XTP_ORDER_STATUS_INIT:
                return OrderStatusType.INIT;
            case XTP_ORDER_STATUS_NOTRADEQUEUEING:
                return OrderStatusType.NOTRADEQUEUEING;
            case XTP_ORDER_STATUS_CANCELED:
                return OrderStatusType.CANCELED;
            case XTP_ORDER_STATUS_PARTTRADEDNOTQUEUEING:
                return OrderStatusType.PARTTRADEDNOTQUEUEING;
            case XTP_ORDER_STATUS_PARTTRADEDQUEUEING:
                return OrderStatusType.PARTTRADEDQUEUEING;
            case XTP_ORDER_STATUS_UNKNOWN:
                return OrderStatusType.UNKNOWN;
        }
        return null;
    }

    public MarketType converterMarketType(ExchangeType exchangeType) {
        switch (exchangeType) {
            case SH:
                return MarketType.SH;
            case SZ:
                return MarketType.SZ;
        }
        return null;
    }

    public BigDecimal converterDouble(double lastPrice){
        return new BigDecimal(lastPrice);
    }
}
