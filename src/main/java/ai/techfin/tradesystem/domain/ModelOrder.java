package ai.techfin.tradesystem.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;

@Embeddable
public class ModelOrder {

    @Embedded
    private Stock stock;

    @Column(precision = 16, scale = 15)
    private BigDecimal weight;

    public ModelOrder() {
    }

    public ModelOrder(Stock stock, BigDecimal weight) {
        this.stock = stock;
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Stock getStock() { return stock; }

    public void setStock(Stock stock) { this.stock = stock; }

}
