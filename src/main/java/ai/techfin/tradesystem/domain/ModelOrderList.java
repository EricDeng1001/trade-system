package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.aop.validation.group.PERSIST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "model_order_list")
public class ModelOrderList {

    private static final Logger logger = LoggerFactory.getLogger(ModelOrder.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = PERSIST.class)
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Nullable
    @ElementCollection(targetClass = ModelOrder.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "model_order_list_data",
                     joinColumns = @JoinColumn(name = "list_id", referencedColumnName = "id"))
    private Set<ModelOrder> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "product_orders",
               joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private ProductAccount productAccount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlacementList placementList;

    public ModelOrderList(String model, ProductAccount productAccount,
                          @Nullable Set<ModelOrder> orders) {
        logger.debug("A model order list is created with full data");
        this.model = model;
        this.orders = orders;
        setProductAccount(productAccount);
    }

    public ModelOrderList() {
        logger.debug("A model order list is created");
    }

    private void removeRelationship(PlacementList modelOrderList) {

    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public PlacementList getPlacementList() { return placementList; }

    public void setPlacementList(PlacementList placementList) {
        if (placementList == this.placementList) {
            return;
        }

        var origin = this.placementList;
        if (placementList == null) {
            this.placementList = null;
            origin.setModelOrderList(null);
        } else {
            this.placementList = placementList;
            // do that side
            placementList.setModelOrderList(this);
            // do origin side
            if (origin != null) {
                origin.setModelOrderList(null);
            }

            var thatOrigin = placementList.getModelOrderList();
            if (thatOrigin != null) {
                thatOrigin.setPlacementList(null);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        logger.debug("Setting id");
        this.id = id;
    }

    public ProductAccount getProductAccount() {
        return productAccount;
    }

    public void setProductAccount(ProductAccount productAccount) {
        this.productAccount = productAccount;
        productAccount.addModelOrderList(this);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Set<ModelOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<ModelOrder> orders) {
        logger.debug("Setting model orders for id: {}", id);
        this.orders = orders;
    }

}
