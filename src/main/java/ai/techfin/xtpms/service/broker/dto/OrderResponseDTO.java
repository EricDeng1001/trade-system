package ai.techfin.xtpms.service.broker.dto;

import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.OrderStatusType;
import ai.techfin.tradesystem.domain.enums.PriceType;
import ai.techfin.tradesystem.domain.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO implements Serializable {

    private Long placementId;

    private String user;

    private Stock stock;

    private String errorId;

    private String errorMsg;

    private double price;

    private long quantity;

    private PriceType priceType;

    private TradeType tradeType;
    /**
     * 订单状态
     */
    private OrderStatusType statusType;

}
