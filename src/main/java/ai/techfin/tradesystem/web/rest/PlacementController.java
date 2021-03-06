package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.PlacementList;
import ai.techfin.tradesystem.domain.enums.TradeType;
import ai.techfin.tradesystem.repository.PlacementListRepository;
import ai.techfin.tradesystem.security.AuthoritiesConstants;
import ai.techfin.tradesystem.service.ModelOrderService;
import ai.techfin.tradesystem.service.PlacementService;
import ai.techfin.tradesystem.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlacementController {

    private static final Logger log = LoggerFactory.getLogger(PlacementController.class);

    private final ModelOrderService modelOrderService;

    private final TradeService tradeService;

    private final PlacementService placementService;

    @Autowired
    public PlacementController(ModelOrderService modelOrderService, TradeService tradeService,
                               PlacementService placementService) {
        this.modelOrderService = modelOrderService;
        this.tradeService = tradeService;
        this.placementService = placementService;
    }

    @PostMapping("/placement-list/{tradeType}")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(AuthoritiesConstants.TRADER)
    public PlacementList createSellPlacementList(@PathVariable TradeType tradeType, @RequestParam Long modelOrderListId) {
        modelOrderService.existsOrThrow(modelOrderListId);
        return tradeService.process(modelOrderListId, tradeType);
    }

    @GetMapping("/placement-list/{id}")
    @Secured({AuthoritiesConstants.TRADER, AuthoritiesConstants.ADMIN})
    public PlacementList getPlacementList(@PathVariable Long id) {
        return placementService.findNotNull(id);
    }
}
