package ai.techfin.tradesystem.web.websocket.dto;

import ai.techfin.tradesystem.domain.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class PriceUpdateDTO {

    private static final Logger log = LoggerFactory.getLogger(PriceUpdateDTO.class);

    private Stock stock;

    private BigDecimal price;

    public Stock getStock() { return stock; }

    public void setStock(Stock stock) { this.stock = stock; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

}
