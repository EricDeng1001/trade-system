package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.domain.ModelOrder;
import ai.techfin.tradesystem.dto.ModelOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class ModelOrderService {

    private static final Logger log = LoggerFactory.getLogger(ModelOrderService.class);

    public ModelOrderDTO loadDTOWithNewestPrice(ModelOrder modelOrder, BigDecimal totalAsset) {
        // TODO: 从消息中心获取价格
        final BigDecimal price = BigDecimal.valueOf(2);
        final BigDecimal weight = modelOrder.getWeight();
        BigDecimal money;
        if (weight.compareTo(BigDecimal.ZERO) > 0) {
            money = totalAsset.multiply(weight);
        } else {
            // TODO: 取得股票数量
            final BigInteger quantity = BigInteger.valueOf(1000);
            money = price.multiply(new BigDecimal(quantity));
        }
        return new ModelOrderDTO(modelOrder.getStock(), price, money);
    }

}
