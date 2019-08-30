package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.domain.enums.MarketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Stock {

    private static final Logger log = LoggerFactory.getLogger(Stock.class);

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
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
