package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.domain.enums.TradeType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ModelOrder {

    @Embedded
    private Stock stock;

    @Column(precision = 21, scale = 2)
    private BigDecimal money;

    @Column(name = "trade_type", columnDefinition = "tinyint")
    @Enumerated(value = EnumType.ORDINAL)
    private TradeType tradeType;

    @Override
    public int hashCode() {
        return Objects.hashCode(stock);
    }

    public ModelOrder(Stock stock, BigDecimal money, TradeType tradeType) {
        this.stock = stock;
        this.money = money;
        this.tradeType = tradeType;
    }

    public ModelOrder() {
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public Stock getStock() { return stock; }

    public void setStock(Stock stock) { this.stock = stock; }

}
