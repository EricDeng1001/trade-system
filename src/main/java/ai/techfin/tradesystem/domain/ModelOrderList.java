package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.aop.validation.group.PERSIST;
import ai.techfin.tradesystem.domain.enums.TradeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ElementCollection(targetClass = ModelOrder.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "model_order_list_data",
                     joinColumns = @JoinColumn(name = "list_id", referencedColumnName = "id"))
    private Set<ModelOrder> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "product_orders",
               joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Product product;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlacementList placementList;

    public ModelOrderList(String model, Product product,
                          @NotNull Set<ModelOrder> orders) {
        logger.debug("A model order list is created with full data");
        this.model = model;
        this.orders = orders;
        setProduct(product);
    }

    public ModelOrderList() {
        logger.debug("A model order list is created");
    }

    public Set<ModelOrder> getSellList() {
        return orders.stream()
            .filter(order -> order.getTradeType() == TradeType.SELL)
            .collect(Collectors.toSet());
    }

    public Set<ModelOrder> getBuyList() {
        return orders.stream()
            .filter(order -> order.getTradeType() == TradeType.BUY)
            .collect(Collectors.toSet());
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        product.addModelOrderList(this);
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
