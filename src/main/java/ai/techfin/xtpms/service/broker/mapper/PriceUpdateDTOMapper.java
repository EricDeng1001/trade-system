package ai.techfin.xtpms.service.broker.mapper;

import ai.techfin.tradesystem.service.dto.PriceUpdateDTO;
import ai.techfin.xtpms.service.broker.utils.ClassConverterUtils;
import com.zts.xtp.quote.model.response.DepthMarketDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ClassConverterUtils.class)
public interface PriceUpdateDTOMapper {

    @Mapping(target = "stock.name", source = "data.ticker")
    @Mapping(target = "stock.market", source = "data.exchangeType")
    @Mapping(target = "price", source = "data.lastPrice")
    @Mapping(target = "broker", ignore = true)
    PriceUpdateDTO priceToPriceDto(DepthMarketDataResponse data);
}
