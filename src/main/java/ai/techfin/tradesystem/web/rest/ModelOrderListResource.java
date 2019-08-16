package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.ModelOrder;
import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.enums.MarketType;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.security.AuthoritiesConstants;
import ai.techfin.tradesystem.web.rest.vm.ModelOrderListTwoDimArrayVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ModelOrderListResource {

    private static final Logger logger = LoggerFactory.getLogger(ModelOrderListResource.class);

    private final ModelOrderListRepository modelOrderListRepository;

    @Autowired
    public ModelOrderListResource(ModelOrderListRepository modelOrderListRepository) {
        this.modelOrderListRepository = modelOrderListRepository;
    }

    @PostMapping("/model-order-list")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(AuthoritiesConstants.MODEL)
    public void create(@RequestBody ModelOrderListTwoDimArrayVM vm) {
        ModelOrderList created = new ModelOrderList();
        created.setModel(vm.getModel());
        Set<ModelOrder> orders = new HashSet<>();
        for (String[] orderData : vm.getData()) {
            // stock.market
            logger.debug("{}, {}, {}", orderData[1], Double.parseDouble(orderData[1]), BigDecimal.valueOf(Double.parseDouble(orderData[1])));
            int splitPoint = orderData[0].indexOf('.');
            orders.add(new ModelOrder(
                orderData[0].substring(0, splitPoint),
                MarketType.valueOf(orderData[0].substring(splitPoint + 1)),
                BigDecimal.valueOf(Double.parseDouble(orderData[1]))
            ));
        }
        created.setOrders(orders);
        modelOrderListRepository.save(created);
    }

}
