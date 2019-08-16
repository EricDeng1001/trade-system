package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.domain.enums.MarketType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Embeddable
public class ModelOrder {

    @Column(name = "stock")
    private String stock;

    @Column(name = "market")
    @Enumerated(EnumType.STRING)
    private MarketType market;

    @Column(name = "weight", precision = 15, scale = 15)
    private BigDecimal weight;

    public ModelOrder() {
    }

    public ModelOrder(String stock, MarketType market, BigDecimal weight) {
        this.stock = stock;
        this.market = market;
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public MarketType getMarket() {
        return market;
    }

    public void setMarket(MarketType market) {
        this.market = market;
    }

}
