package ai.techfin.xtpms.service.broker.utils;

import com.zts.xtp.common.enums.MarketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TickerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TickerHelper.class);

    public static MarketType getMarket(String ticker) {
        switch (ticker.substring(0, 3)) {
            case "600":
            case "601":
            case "603":
            case "900":
            case "580":
                return MarketType.XTP_MKT_SH_A;
            default:
                return MarketType.XTP_MKT_SZ_A;
        }
    }

    public static String toTickerDotMarket(String ticker, MarketType marketType) {
        switch (marketType) {
            case XTP_MKT_SZ_A:
                return ticker + "." + "SZ";
            default:
                return ticker + "." + "SH";
        }
    }

}
