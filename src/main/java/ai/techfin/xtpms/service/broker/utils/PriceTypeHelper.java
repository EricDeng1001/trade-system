package ai.techfin.xtpms.service.broker.utils;

import com.zts.xtp.common.enums.PriceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceTypeHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceTypeHelper.class);

    public static PriceType getPriceType(String type){
        type = type.toLowerCase();
        switch (type) {
            case "five_level":
                return PriceType.XTP_PRICE_BEST5_OR_CANCEL;
            default:
                return PriceType.XTP_PRICE_LIMIT;
        }
    }

}
