package ai.techfin.xtpms.service.broker.mapper;

import ai.techfin.xtpms.service.broker.dto.TradeResponseDTO;
import ai.techfin.xtpms.service.broker.utils.ClassConverterUtils;
import com.zts.xtp.trade.model.response.TradeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = ClassConverterUtils.class)
public interface TradeRespDTOMapper {


    @Mapping(target = "quantity", source = "tradeResponse.quantity")
    @Mapping(target = "price", source = "tradeResponse.price")
    @Mapping(target = "stock.name", source = "tradeResponse.ticker")
    @Mapping(target = "stock.market",source = "tradeResponse.marketType")
    @Mapping(target = "placementId", ignore = true)
    TradeResponseDTO tradeToTradeDTO(TradeResponse tradeResponse);
}
