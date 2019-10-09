package ai.techfin.tradesystem.service.dto;

import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.BrokerType;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PriceUpdateDTO implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(PriceUpdateDTO.class);

    private static final long serialVersionUID = -3765882767452441496L;

    private Stock stock;

    private BigDecimal price;

    private BrokerType broker;

    public PriceUpdateDTO(Stock stock, BigDecimal price, BrokerType broker) {
        this.stock = stock;
        this.price = price;
        this.broker = broker;
    }

    public Stock getStock() {
        return stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BrokerType getBroker() {
        return broker;
    }


    public PriceUpdateDTO(){}

}
