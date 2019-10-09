package ai.techfin.xtpms.service.broker.dto;

import ai.techfin.tradesystem.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@ToString
@AllArgsConstructor
public class TradeResponseDTO implements Serializable {

    @Getter
    private Long placementId;

    @Getter
    private Long quantity;

    @Getter
    private BigDecimal price;

    @Getter
    private Stock stock;

}
