package ai.techfin.tradesystem.service.dto;

import ai.techfin.tradesystem.domain.ModelOrder;

import java.math.BigDecimal;

public class ModelOrderDTO {

    private ModelOrder modelOrder;

    private BigDecimal price;

    public ModelOrderDTO(ModelOrder modelOrder, BigDecimal price) {
        this.modelOrder = modelOrder;
        this.price = price;
    }

    public ModelOrder getModelOrder() {
        return modelOrder;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
