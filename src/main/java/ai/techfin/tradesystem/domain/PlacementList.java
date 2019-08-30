package ai.techfin.tradesystem.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "placement_list")
public class PlacementList {

    private static final Logger log = LoggerFactory.getLogger(PlacementList.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @ElementCollection(targetClass = Placement.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "placement_list_data",
                     joinColumns = @JoinColumn(name = "list_id", referencedColumnName = "id"))
    private Set<Placement> placements;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ModelOrderList modelOrderList;

    public PlacementList(Set<Placement> placements) {
        this.placements = placements;
    }

    public PlacementList() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public ModelOrderList getModelOrderList() { return modelOrderList; }

    public void setModelOrderList(ModelOrderList modelOrderList) {
        if (this.modelOrderList != null) {
            this.modelOrderList.setPlacementList(null);
        }
        this.modelOrderList = modelOrderList;
        if (modelOrderList == null) { return; }
        final PlacementList placementList = modelOrderList.getPlacementList();
        if (placementList != this) {
            if (placementList != null) {
                placementList.setModelOrderList(null);
            }
            modelOrderList.setPlacementList(this);
        }
    }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Set<Placement> getPlacements() { return placements; }

    public void setPlacements(Set<Placement> placements) { this.placements = placements; }

}
