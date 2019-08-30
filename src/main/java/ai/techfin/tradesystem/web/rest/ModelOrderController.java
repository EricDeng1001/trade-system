package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.ModelOrder;
import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.ProductAccount;
import ai.techfin.tradesystem.domain.Stock;
import ai.techfin.tradesystem.domain.enums.MarketType;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.repository.ProductAccountRepository;
import ai.techfin.tradesystem.security.AuthoritiesConstants;
import ai.techfin.tradesystem.service.ModelOrderService;
import ai.techfin.tradesystem.service.dto.ModelOrderDTO;
import ai.techfin.tradesystem.web.rest.errors.ResourceNotExistException;
import ai.techfin.tradesystem.web.rest.vm.ModelOrderListTwoDimArrayVM;
import ai.techfin.tradesystem.web.rest.vm.ModelOrderListVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    private final ModelOrderService modelOrderService;

    @Autowired
    public ModelOrderController(ModelOrderListRepository modelOrderListRepository,
                                ProductAccountRepository productAccountRepository,
                                ModelOrderService modelOrderService) {
        this.modelOrderListRepository = modelOrderListRepository;
        this.productAccountRepository = productAccountRepository;
        this.modelOrderService = modelOrderService;
    }

    @PostMapping("/model-order-list")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(AuthoritiesConstants.MODEL)
    public void create(@RequestBody ModelOrderListTwoDimArrayVM vm) {
        String productName = vm.getProduct();
        Optional<ProductAccount> productAccount = productAccountRepository.findByName(productName);
        if (productAccount.isEmpty()) {
            throw new ResourceNotExistException();
        }

        Set<ModelOrder> orders = new HashSet<>();
        for (String[] orderData : vm.getData().getBuyList()) {
            // format is "stock.market"
            logger.debug("{}, {}, {}", orderData[1], Double.parseDouble(orderData[1]),
                         BigDecimal.valueOf(Double.parseDouble(orderData[1])));
            int splitPoint = orderData[0].indexOf('.');
            orders.add(new ModelOrder(
                new Stock(orderData[0].substring(0, splitPoint),
                          MarketType.valueOf(orderData[0].substring(splitPoint + 1))),
                new BigDecimal(orderData[1])
            ));
        }
        for (String[] orderData : vm.getData().getSellList()) {
            // format is "stock.market"
            logger.debug("{}, {}, {}", orderData[1], Double.parseDouble(orderData[1]),
                         BigDecimal.valueOf(Double.parseDouble(orderData[1])));
            int splitPoint = orderData[0].indexOf('.');
            orders.add(new ModelOrder(
                new Stock(orderData[0].substring(0, splitPoint),
                          MarketType.valueOf(orderData[0].substring(splitPoint + 1))),
                new BigDecimal("-" + orderData[1])
            ));
        }
        ModelOrderList created = new ModelOrderList(vm.getModel(), productAccount.get(), orders);
        logger.info("going to save: {}", created);
        modelOrderListRepository.save(created);
    }

    @GetMapping("/model-order-list")
    @Secured(AuthoritiesConstants.TRADER)
    public List<ModelOrderListVM> queryPlacementList(
        @RequestParam @NotNull Instant begin,
        @RequestParam @NotNull Instant end,
        @RequestParam @NotNull Long productId
    ) {
        logger.info("going to select between: {} to {}", begin, end);
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
                List<ModelOrderDTO> placements = orderList.getOrders().stream().map(
                    order -> modelOrderService.loadDTOWithNewestPrice(order, totalAsset)
                ).collect(Collectors.toList());
                return new ModelOrderListVM(placements, orderList.getModel(), product.get().getName(),
                                            orderList.getCreatedAt(), orderList.getId());
            }
        ).collect(Collectors.toList());
    }

}
