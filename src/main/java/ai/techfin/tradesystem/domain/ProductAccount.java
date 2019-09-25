package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.domain.enums.BrokerType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProductAccount.
 */
@Entity
@Table(name = "product_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductAccount implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ProductAccount.class);

    private static final long serialVersionUID = 665934918284011794L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

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
    private Instant createdAt = Instant.now();

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "product_in_xtp",
               joinColumns = @JoinColumn(name = "product", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "xtp_account", referencedColumnName = "account"))
    private XtpAccount xtpAccount;

    @ManyToMany(
        cascade = {CascadeType.MERGE, CascadeType.PERSIST},
        fetch = FetchType.LAZY)
    @JoinTable(name = "user_product",
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<User> managers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_orders",
               inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
               joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<ModelOrderList> modelOrderLists = new HashSet<>();

    public ProductAccount() {
    }

    /**
     * simply add a MOL relation to this PA
     *
     * @param modelOrderList the object to build relation with
     */
    public void addModelOrderList(ModelOrderList modelOrderList) {
        modelOrderLists.add(modelOrderList);
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
        return 42;
    }

    @Override
    public String toString() {
        return "ProductAccount{" +
            "\n\tid=" + getId() +
            "\n\tproduct=" + getName() +
            "\n\tinitialAsset=" + getInitialAsset() +
            "\n\ttotalAsset=" + getTotalAsset() +
            "\n\tcreatedAt=" + getCreatedAt() +
            "\n\tprovider=" + getBrokerType() +
            "\n}";
    }

    public void addManager(User user) {
        logger.debug("add a manager");
        this.managers.add(user);
        if (!user.canManageProduct(this)) {
            user.addManagedProduct(this);
        }
    }

    public boolean managedBy(User user) {
        return this.managers.contains(user);
    }

    public Set<ModelOrderList> getModelOrderLists() {
        return modelOrderLists;
    }

    public XtpAccount getXtpAccount() {
        return xtpAccount;
    }

    public void setModelOrderLists(Set<ModelOrderList> modelOrderLists) {
        this.modelOrderLists = modelOrderLists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getManagers() {
        return managers;
    }

    public void setManagers(Set<User> managers) {
        logger.debug("setting managers");
        this.managers.clear();
        managers.forEach(this::addManager);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInitialAsset() {
        return initialAsset;
    }

    public void setInitialAsset(BigDecimal initialAsset) {
        this.initialAsset = initialAsset;
    }

    public BigDecimal getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(BigDecimal totalAsset) {
        this.totalAsset = totalAsset;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public BrokerType getBrokerType() {
        return brokerType;
    }

    public void setBrokerType(BrokerType brokerType) {
        this.brokerType = brokerType;
    }

    public void setXtpAccount(XtpAccount xtpAccount) {
        this.xtpAccount = xtpAccount;
    }

}
