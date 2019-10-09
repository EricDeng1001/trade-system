package ai.techfin.xtpms.service.broker.mapper;

import ai.techfin.xtpms.service.broker.dto.OrderResponseDTO;
import ai.techfin.xtpms.service.broker.utils.ClassConverterUtils;
import com.zts.xtp.common.model.ErrorMessage;
import com.zts.xtp.trade.model.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ClassConverterUtils.class)
public interface OrderRespDTOMapper {

    @Mapping(source = "errorMessage.errorId", target = "errorId")
    @Mapping(source = "errorMessage.errorMsg", target = "errorMsg")
    @Mapping(source = "orderResponse.orderStatusType", target = "statusType")
    @Mapping(source = "orderResponse.price", target = "price")
    @Mapping(source = "orderResponse.quantity", target = "quantity")
    @Mapping(source = "orderResponse.priceType", target = "priceType")
    @Mapping(source = "orderResponse.sideType", target = "tradeType")
    @Mapping(source = "orderResponse.ticker", target = "stock.name")
    @Mapping(source = "orderResponse.marketType", target = "stock.market")
    @Mapping(target = "user" , ignore = true)
    @Mapping(target = "placementId", ignore = true)
    OrderResponseDTO orderToOrderDTO(OrderResponse orderResponse, ErrorMessage errorMessage);
}
