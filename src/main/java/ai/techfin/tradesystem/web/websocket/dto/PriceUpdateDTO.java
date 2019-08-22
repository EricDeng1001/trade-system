package ai.techfin.tradesystem.web.websocket.dto;

import ai.techfin.tradesystem.domain.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class PriceUpdateDTO {

    private static final Logger log = LoggerFactory.getLogger(PriceUpdateDTO.class);

    private Stock stock;

    private BigDecimal price;

    private String broker;

    public PriceUpdateDTO(Stock stock, BigDecimal price, String broker) {
        this.stock = stock;
        this.price = price;
        this.broker = broker;
    }

    public Stock getStock() { return stock; }

    public void setStock(Stock stock) { this.stock = stock; }

    public String getBroker() { return broker; }

    public void setBroker(String broker) { this.broker = broker; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

}
