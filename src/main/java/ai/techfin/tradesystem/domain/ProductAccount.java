package ai.techfin.tradesystem.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A ProductAccount.
 */
@Entity
@Table(name = "product_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "product", nullable = false, unique = true)
    private String product;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "initial_asset", precision = 21, scale = 2, nullable = false)
    private BigDecimal initialAsset;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_asset", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalAsset;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "provider", nullable = false)
    private String provider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public ProductAccount product(String product) {
        this.product = product;
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getInitialAsset() {
        return initialAsset;
    }

    public ProductAccount initialAsset(BigDecimal initialAsset) {
        this.initialAsset = initialAsset;
        return this;
    }

    public void setInitialAsset(BigDecimal initialAsset) {
        this.initialAsset = initialAsset;
    }

    public BigDecimal getTotalAsset() {
        return totalAsset;
    }

    public ProductAccount totalAsset(BigDecimal totalAsset) {
        this.totalAsset = totalAsset;
        return this;
    }

    public void setTotalAsset(BigDecimal totalAsset) {
        this.totalAsset = totalAsset;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ProductAccount createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getProvider() {
        return provider;
    }

    public ProductAccount provider(String provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductAccount)) {
            return false;
        }
        return id != null && id.equals(((ProductAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductAccount{" +
            "id=" + getId() +
            ", product='" + getProduct() + "'" +
            ", initialAsset=" + getInitialAsset() +
            ", totalAsset=" + getTotalAsset() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", provider='" + getProvider() + "'" +
            "}";
    }
}
