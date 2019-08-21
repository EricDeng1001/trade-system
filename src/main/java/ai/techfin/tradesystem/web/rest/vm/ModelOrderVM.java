package ai.techfin.tradesystem.web.rest.vm;

import ai.techfin.tradesystem.domain.enums.MarketType;

import java.math.BigDecimal;

public class ModelOrderVM {

    private String stock;

    private MarketType market;

    private BigDecimal price;

    private BigDecimal money;

    public ModelOrderVM(String stock, MarketType market, BigDecimal price, BigDecimal money) {
        this.stock = stock;
        this.market = market;
        this.price = price;
        this.money = money;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

}
