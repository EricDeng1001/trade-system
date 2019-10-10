package ai.techfin.tradesystem.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;
import java.time.Instant;

@Embeddable
public class Placement {

    private static final Logger log = LoggerFactory.getLogger(Placement.class);

    @Embedded
    private Stock stock;

    @Column(nullable = false)
    private Long quantity = 0L;

    /**
     * represent total quantity dealt in this placement,
     * no matter if their price is the same
     */
    @Column(name = "dealt_q")
    private Long quantityDealt = 0L;

    /**
     * total money dealt with this placement
     */
    @Column(name = "dealt_m", precision = 21, scale = 2)
    private BigDecimal moneyDealt;

    @Column(nullable = false, precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public Placement() {}

    public Placement(Stock stock, Long quantity, BigDecimal price) {
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getQuantityDealt() {
        return quantityDealt;
    }

    public void setQuantityDealt(Long quantityDealt) {
        this.quantityDealt = quantityDealt;
    }

    public BigDecimal getMoneyDealt() {
        return moneyDealt;
    }

    public void setMoneyDealt(BigDecimal moneyDealt) {
        this.moneyDealt = moneyDealt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Stock getStock() { return stock; }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getQuantity() { return quantity; }

}
