package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.ModelOrder;
import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.ProductAccount;
import ai.techfin.tradesystem.domain.enums.MarketType;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.repository.ProductAccountRepository;
import ai.techfin.tradesystem.security.AuthoritiesConstants;
import ai.techfin.tradesystem.web.rest.vm.ModelOrderListTwoDimArrayVM;
import ai.techfin.tradesystem.web.rest.vm.ModelOrderListVM;
import ai.techfin.tradesystem.web.rest.vm.ModelOrderVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ModelOrderController {

    private static final Logger logger = LoggerFactory.getLogger(ModelOrderController.class);

    private final ModelOrderListRepository modelOrderListRepository;

    private final ProductAccountRepository productAccountRepository;

    @Autowired
    public ModelOrderController(ModelOrderListRepository modelOrderListRepository,
                                ProductAccountRepository productAccountRepository) {
        this.modelOrderListRepository = modelOrderListRepository;
        this.productAccountRepository = productAccountRepository;
    }

    @PostMapping("/model-order-list")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(AuthoritiesConstants.MODEL)
    public void create(@RequestBody ModelOrderListTwoDimArrayVM vm) {
        String productName = vm.getProduct();
        Optional<ProductAccount> productAccount = productAccountRepository.findByName(productName);
        if (productAccount.isEmpty()) {
            // TODO: throw an error
            return;
        }

        Set<ModelOrder> orders = new HashSet<>();
        for (String[] orderData : vm.getData()) {
            // format is "stock.market"
            logger.debug("{}, {}, {}", orderData[1], Double.parseDouble(orderData[1]),
                         BigDecimal.valueOf(Double.parseDouble(orderData[1])));
            int splitPoint = orderData[0].indexOf('.');
            orders.add(new ModelOrder(
                orderData[0].substring(0, splitPoint),
                MarketType.valueOf(orderData[0].substring(splitPoint + 1)),
                new BigDecimal(orderData[1])
            ));
        }
        ModelOrderList created = new ModelOrderList(vm.getModel(), productAccount.get(), orders);
        modelOrderListRepository.save(created);
    }

    @GetMapping("/model-order-list")
    @Secured(AuthoritiesConstants.TRADER)
    public List<ModelOrderListVM> queryPlacementList(
        @RequestParam @NotNull Instant begin,
        @RequestParam @NotNull Instant end,
        @RequestParam @NotNull Long productId
    ) {
        Optional<ProductAccount> product = productAccountRepository.findById(productId);
        if (product.isEmpty()) {
            return null;
        }
        List<ModelOrderList> orderLists = modelOrderListRepository
            .findByCreatedAtBetweenAndProductAccount(begin, end, product.get());

        if (orderLists.size() == 0) {
            return null;
        }

        BigDecimal totalAsset = product.get().getTotalAsset();

        return orderLists.stream().map(
            orderList -> {
                List<ModelOrderVM> placements = orderList.getOrders().stream().map(
                    order -> {
                        // TODO: 从消息中心获取价格
                        BigDecimal price = BigDecimal.valueOf(2);
                        BigInteger quantity = totalAsset
                            .multiply(order.getWeight())
                            // 买少不买多 Floor 模式
                            .divide(price, RoundingMode.FLOOR)
                            .toBigInteger();
                        return new ModelOrderVM(order.getStock(), order.getMarket(), price, quantity);
                    }
                ).collect(Collectors.toList());
                return new ModelOrderListVM(placements, orderList.getModel(), product.get().getName(),
                                            orderList.getCreatedAt());
            }
        ).collect(Collectors.toList());
    }

}
