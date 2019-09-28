package ai.techfin.xtpms.service.broker.mapper;

import ai.techfin.xtpms.service.broker.dto.TradeResponseDTO;
import ai.techfin.xtpms.service.broker.utils.ClassConverterUtils;
import com.zts.xtp.trade.model.response.TradeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = ClassConverterUtils.class)
public interface TradeRespDTOMapper {

    @Mapping(target = "placementId", source = "tradeResponse.orderXtpId")
    @Mapping(target = "tradeId", source = "tradeResponse.execId")
    @Mapping(target = "quantity", source = "tradeResponse.quantity")
    @Mapping(target = "price", source = "tradeResponse.price")
    @Mapping(target = "ticker", source = "tradeResponse.ticker")
    @Mapping(target = "sideType",source = "tradeResponse.sideType")
    @Mapping(target = "marketType",source = "tradeResponse.marketType")
    TradeResponseDTO tradeToTradeDTO(TradeResponse tradeResponse);
}
