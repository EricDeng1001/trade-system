package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.TradeSystemApp;
import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.PlacementList;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = TradeSystemApp.class)
@EmbeddedKafka
@ActiveProfiles("test")
class ModelOrderListRepositoryIT {
    @Autowired
    private ModelOrderListRepository modelOrderListRepository;

    @Autowired
    private PlacementListRepository placementListRepository;


    @BeforeEach
    void testUpdateRelation() {
        ModelOrderList modelOrderList = new ModelOrderList();
        modelOrderList.setModel("test");
        modelOrderListRepository.save(modelOrderList);
    }

    @Test
    void testCreateRelation() {
        List<ModelOrderList> modelOrderLists = modelOrderListRepository.findByModel("test");
        PlacementList placementList = new PlacementList();
        placementList = placementListRepository.save(placementList);
        placementList.setModelOrderList(modelOrderLists.get(0));
        assert modelOrderLists.get(0).getPlacementList() == placementList;
        placementListRepository.save(placementList);
        assert modelOrderListRepository.findByModel("test").get(0).getPlacementList().equals(placementList);

        placementList = new PlacementList();
        ModelOrderList modelOrderList = new ModelOrderList();
        modelOrderList.setModel("y");
        placementList.setModelOrderList(modelOrderList);
        placementListRepository.save(placementList);
        assert modelOrderListRepository.findByModel("y").get(0).getPlacementList().equals(placementList);

    }
}
