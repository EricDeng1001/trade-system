package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.domain.ModelOrder;
import ai.techfin.tradesystem.service.dto.ModelOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ModelOrderService {

    private static final Logger log = LoggerFactory.getLogger(ModelOrderService.class);

    public ModelOrderDTO loadDTOWithNewestPrice(ModelOrder modelOrder, BigDecimal totalAsset) {
        // TODO: 从消息中心获取价格
        BigDecimal price = BigDecimal.valueOf(2);
        BigDecimal money = totalAsset.multiply(modelOrder.getWeight());
        return new ModelOrderDTO(modelOrder.getStock(), modelOrder.getMarket(), price, money);
    }

}
