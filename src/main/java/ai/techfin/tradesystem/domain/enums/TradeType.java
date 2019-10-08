package ai.techfin.tradesystem.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TradeType {
    BUY,
    SELL;

    @JsonCreator
    public TradeType fromString(String x) {
        return TradeType.valueOf(x);
    }
}
