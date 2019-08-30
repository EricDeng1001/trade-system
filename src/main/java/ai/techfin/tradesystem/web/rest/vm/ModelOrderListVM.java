package ai.techfin.tradesystem.web.rest.vm;

import ai.techfin.tradesystem.dto.ModelOrderDTO;

import java.time.Instant;
import java.util.List;

public class ModelOrderListVM {

    private List<ModelOrderDTO> placements;

    private String model;

    private String product;

    private Instant createdAt;

    public ModelOrderListVM(List<ModelOrderDTO> placements, String model, String product, Instant createdAt) {
        this.placements = placements;
        this.model = model;
        this.product = product;
        this.createdAt = createdAt;
    }

    public List<ModelOrderDTO> getPlacements() {
        return placements;
    }

    public void setPlacements(List<ModelOrderDTO> placements) {
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
