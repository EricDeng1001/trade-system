package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.domain.ModelOrder;
import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.PlacementList;
import ai.techfin.tradesystem.domain.Product;
import ai.techfin.tradesystem.domain.enums.BrokerType;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.service.dto.ModelOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ModelOrderService {

    private static final Logger log = LoggerFactory.getLogger(ModelOrderService.class);

    private final PriceService priceService;

    private final TradeService tradeService;

    private final ModelOrderListRepository modelOrderListRepository;

    @Autowired
    public ModelOrderService(PriceService priceService, TradeService tradeService,
                             ModelOrderListRepository modelOrderListRepository) {
        this.priceService = priceService;
        this.tradeService = tradeService;
        this.modelOrderListRepository = modelOrderListRepository;
    }

    public ModelOrderDTO createDTO(ModelOrder modelOrder, BrokerType brokerType) {
        return new ModelOrderDTO(modelOrder, priceService.getPrice(modelOrder.getStock(), brokerType));
    }

    public void prepareTrade(ModelOrderList modelOrderList) {
        Product product = modelOrderList.getProduct();
        tradeService.loginProductAccount(product);
        modelOrderList.getOrders()
            .forEach(o -> tradeService.subscribeStockPrice(o.getStock(), product.getProvider()));
    }

    public ModelOrderList find(Long id) {
        return modelOrderListRepository.findById(id).orElse(null);
    }

    public ModelOrderList findNotNull(Long id) {
        return modelOrderListRepository.findById(id).orElseThrow();
    }

    public void existsOrThrow(Long id) {
        if (!modelOrderListRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
    }
}
