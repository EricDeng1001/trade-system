package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.domain.enums.MarketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Stock {

    private static final Logger log = LoggerFactory.getLogger(Stock.class);

    @Column(name = "stock", nullable = false)
    private String name;

    @Column(name = "market", nullable = false)
    private MarketType market;

    public Stock(String name, MarketType market) {
        this.name = name;
        this.market = market;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public MarketType getMarket() { return market; }

    public void setMarket(MarketType market) { this.market = market; }

}
