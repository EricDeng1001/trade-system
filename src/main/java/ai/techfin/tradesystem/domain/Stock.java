package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.domain.enums.MarketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Stock implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(Stock.class);

    @Override
    public int hashCode() {
        return Objects.hashCode(name + market.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Stock) {
            return name != null && name.equals(((Stock) obj).name);
        }
        return false;
    }

    @Column(name = "stock_name", nullable = false)
    private String name;

    @Column(name = "stock_market",nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private MarketType market;

    public Stock() {
    }

    public Stock(String name, MarketType market) {
        this.name = name;
        this.market = market;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public MarketType getMarket() { return market; }

    public void setMarket(MarketType market) { this.market = market; }

}
