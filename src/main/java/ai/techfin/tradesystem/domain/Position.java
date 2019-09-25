package ai.techfin.tradesystem.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Position {

    @EmbeddedId
    private Stock stock;

    private Long amount;

    @ManyToOne
    private ProductAccount productAccount;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public ProductAccount getProductAccount() {
        return productAccount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}
