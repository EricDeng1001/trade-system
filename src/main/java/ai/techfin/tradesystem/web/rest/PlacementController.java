package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.PlacementList;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.security.AuthoritiesConstants;
import ai.techfin.tradesystem.service.PlacementService;
import ai.techfin.tradesystem.web.rest.errors.ResourceNotExistException;
import ai.techfin.tradesystem.web.rest.vm.PlacementListVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PlacementController {

    private static final Logger log = LoggerFactory.getLogger(PlacementController.class);

    private final ModelOrderListRepository modelOrderListRepository;

    private final PlacementService placementService;

    public PlacementController(ModelOrderListRepository modelOrderListRepository,
                               PlacementService placementService) {
        this.modelOrderListRepository = modelOrderListRepository;
        this.placementService = placementService;
    }

    @PostMapping("/placement-list")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(AuthoritiesConstants.TRADER)
    public void createPlacement(@RequestBody PlacementListVM placementListVM) {
        final long id = placementListVM.getModelOrderListId();
        Optional<ModelOrderList> modelOrderListOp = modelOrderListRepository.findById(id);
        if (modelOrderListOp.isEmpty()) {
            throw new ResourceNotExistException();
        }
        placementService.makePlacement(modelOrderListOp.get(), placementListVM.getPlacements());
    }

    @GetMapping("/placement-list")
    @Secured({AuthoritiesConstants.TRADER, AuthoritiesConstants.ADMIN})
    public List<PlacementList> queryPlacement() {
        return placementService.findAll();
    }
}
