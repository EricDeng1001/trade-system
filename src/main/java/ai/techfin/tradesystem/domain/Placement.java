package ai.techfin.tradesystem.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

@Embeddable
public class Placement {

    private static final Logger log = LoggerFactory.getLogger(Placement.class);

    @Embedded
    private Stock stock;

    @Column(nullable = false)
    private BigInteger quantity;

    @Column(nullable = false, precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public Placement(Stock stock, BigInteger quantity, BigDecimal price) {
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
    }

    public Stock getStock() { return stock; }

    public BigDecimal getPrice() {
        return price;
    }

    public BigInteger getQuantity() { return quantity; }

}
