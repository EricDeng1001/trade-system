package ai.techfin.tradesystem.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;

@Embeddable
public class Placement {

    private static final Logger log = LoggerFactory.getLogger(Placement.class);

    @Embedded
    private Stock stock;

    @Column(name = "money", nullable = false, precision = 16, scale = 15)
    private BigDecimal money;

    @Column(name = "price_min", nullable = false, precision = 21, scale = 2)
    private BigDecimal priceMin;

    @Column(name = "price_max", nullable = false, precision = 21, scale = 2)
    private BigDecimal priceMax;

    public Placement(Stock stock, BigDecimal money) {
        this.stock = stock;
        this.money = money;
    }

    public Stock getStock() { return stock; }

    public void setStock(Stock stock) { this.stock = stock; }

    public BigDecimal getMoney() { return money; }

    public void setMoney(BigDecimal money) { this.money = money; }

}
