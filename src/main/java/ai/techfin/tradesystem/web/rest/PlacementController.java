package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.PlacementList;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.repository.PlacementListRepository;
import ai.techfin.tradesystem.security.AuthoritiesConstants;
import ai.techfin.tradesystem.web.rest.errors.ResourceNotExistException;
import ai.techfin.tradesystem.web.rest.vm.PlacementListVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Controller
@RequestMapping("/api")
public class PlacementController {

    private static final Logger log = LoggerFactory.getLogger(PlacementController.class);

    private final ModelOrderListRepository modelOrderListRepository;

    private final PlacementListRepository placementListRepository;

    public PlacementController(ModelOrderListRepository modelOrderListRepository,
                               PlacementListRepository placementListRepository) {
        this.modelOrderListRepository = modelOrderListRepository;
        this.placementListRepository = placementListRepository;
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
        PlacementList created = new PlacementList();
        ModelOrderList modelOrderList = modelOrderListOp.get();
        created.setModelOrderList(modelOrderList);
        created.setPlacements(placementListVM.getPlacements());
        placementListRepository.save(created);
    }

}
