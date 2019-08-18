package ai.techfin.tradesystem.web.rest.vm;

import java.time.Instant;
import java.util.List;

public class ModelOrderListVM {

    private List<ModelOrderVM> placements;

    private String model;

    private String product;

    private Instant createdAt;

    public ModelOrderListVM(List<ModelOrderVM> placements, String model, String product, Instant createdAt) {
        this.placements = placements;
        this.model = model;
        this.product = product;
        this.createdAt = createdAt;
    }

    public List<ModelOrderVM> getPlacements() {
        return placements;
    }

    public void setPlacements(List<ModelOrderVM> placements) {
        this.placements = placements;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
