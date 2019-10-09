package ai.techfin.xtpms.service.broker.dto;

import ai.techfin.tradesystem.domain.Stock;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeResponseDTO implements Serializable {

    private Long placementId;

    private Long quantity;

    private BigDecimal price;

    private Stock stock;

}
