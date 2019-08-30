package ai.techfin.tradesystem.service.dto;

import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.MarketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceUpdateDTO implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(PriceUpdateDTO.class);

    private static final long serialVersionUID = -3765882767452441496L;

    private Stock stock;

    private BigDecimal price;

    private String broker;

    public PriceUpdateDTO(Stock stock, BigDecimal price, String broker) {
        this.stock = stock;
        this.price = price;
        this.broker = broker;
    }

    public static PriceUpdateDTO fromString(String value) {
        return new PriceUpdateDTO(new Stock("000001", MarketType.SZ),
                                  BigDecimal.valueOf(20 + Math.random() * 10),
                                  "xtp"
        );
    }

    @Override
    public String toString() {
        return stock + "," + price + "," + broker;
    }

    public Stock getStock() { return stock; }

    public void setStock(Stock stock) { this.stock = stock; }

    public String getBroker() { return broker; }

    public void setBroker(String broker) { this.broker = broker; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

}
