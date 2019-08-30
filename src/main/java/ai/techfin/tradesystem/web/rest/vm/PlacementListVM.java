package ai.techfin.tradesystem.web.rest.vm;

import ai.techfin.tradesystem.domain.Placement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class PlacementListVM {

    private static final Logger log = LoggerFactory.getLogger(PlacementListVM.class);

    private Long modelOrderListId;

    private Set<Placement> placements;

    public Long getModelOrderListId() { return modelOrderListId; }

    public void setModelOrderListId(Long modelOrderListId) { this.modelOrderListId = modelOrderListId; }

    public Set<Placement> getPlacements() { return placements; }

    public void setPlacements(Set<Placement> placements) { this.placements = placements; }

}
