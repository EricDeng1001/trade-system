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
    private String stock;

    @Column(name = "market", nullable = false)
    private MarketType market;
}
