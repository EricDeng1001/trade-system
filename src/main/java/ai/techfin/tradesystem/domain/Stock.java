package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.domain.enums.MarketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "stock_name")),
        @AttributeOverride(name = "market", column = @Column(name = "stock_market"))
})
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
            return name != null && name.equals(((Stock) obj).name) && market != null && market == ((Stock) obj).market;
        }
        return false;
    }

    private String name;

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
