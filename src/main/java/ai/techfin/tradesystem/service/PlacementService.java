package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.Placement;
import ai.techfin.tradesystem.domain.PlacementList;
import ai.techfin.tradesystem.repository.PlacementListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PlacementService {

    private static final Logger log = LoggerFactory.getLogger(PlacementService.class);

    private final PlacementListRepository placementListRepository;

    private final KafkaTemplate<String, Long> kafkaTemplate;

    public PlacementService(PlacementListRepository placementListRepository,
                            KafkaTemplate<String, Long> kafkaTemplate) {
        this.placementListRepository = placementListRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void makePlacement(ModelOrderList modelOrderList, Set<Placement> placements) {
        PlacementList created = new PlacementList();
        created.setModelOrderList(modelOrderList);
        created.setPlacements(placements);
        created = placementListRepository.save(created);
        kafkaTemplate.send(KafkaTopicConfiguration.NEW_TRADE_COMMAND, created.getId());
    }

    public List<PlacementList> findAll() {
        return placementListRepository.findAll();
    }

}
