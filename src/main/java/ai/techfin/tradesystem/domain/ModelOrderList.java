package ai.techfin.tradesystem.domain;

import ai.techfin.tradesystem.aop.validation.group.PERSIST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.Set;

@Entity
@Table(name = "model_order_list")
public class ModelOrderList {

    private static final Logger logger = LoggerFactory.getLogger(ModelOrder.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = PERSIST.class)
    @Column(name = "id")
    private Long id;

    @Column(name = "model", nullable = false)
    private String model;

    @ElementCollection(targetClass = ModelOrder.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "model_order_list_data",
                     joinColumns =
                     @JoinColumn(name = "list_id", referencedColumnName = "id"))
    private Set<ModelOrder> orders;

    public ModelOrderList() {
        logger.debug("A model order list is created");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        logger.debug("Setting id");
        this.id = id;
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
