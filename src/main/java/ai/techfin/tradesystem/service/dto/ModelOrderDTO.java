package ai.techfin.tradesystem.service.dto;

import ai.techfin.tradesystem.domain.Stock;

import java.math.BigDecimal;

public class ModelOrderDTO {

    private Stock stock;

    private BigDecimal price;

    private BigDecimal money;

    public ModelOrderDTO(Stock stock, BigDecimal price, BigDecimal money) {
        this.stock = stock;
        this.price = price;
        this.money = money;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Stock getStock() { return stock; }

    public void setStock(Stock stock) { this.stock = stock; }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

}
