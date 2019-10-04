package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.PlacementList;
import ai.techfin.tradesystem.domain.enums.TradeType;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.security.AuthoritiesConstants;
import ai.techfin.tradesystem.service.TradeService;
import ai.techfin.tradesystem.web.rest.errors.ResourceNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PlacementController {

    private static final Logger log = LoggerFactory.getLogger(PlacementController.class);

    private final ModelOrderListRepository modelOrderListRepository;

    private final TradeService tradeService;

    public PlacementController(ModelOrderListRepository modelOrderListRepository,
                               TradeService tradeService) {
        this.modelOrderListRepository = modelOrderListRepository;
        this.tradeService = tradeService;
    }

    @PostMapping("/placement-list/{tradeType}")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(AuthoritiesConstants.TRADER)
    public void createSellPlacementList(@PathVariable String tradeType, @RequestBody Long modelOrderListId) {
        Optional<ModelOrderList> modelOrderListOp = modelOrderListRepository.findById(modelOrderListId);
        if (modelOrderListOp.isEmpty()) {
            throw new ResourceNotExistException();
        }
        tradeService.process(modelOrderListId, TradeType.valueOf(tradeType));
    }

}
