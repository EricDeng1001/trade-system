package ai.techfin.tradesystem.domain;

import org.junit.jupiter.api.Test;


class PlacementListTest {

    @Test
    void testCreateRelation() {
        PlacementList placementList = new PlacementList();
        ModelOrderList modelOrderList = new ModelOrderList();
        placementList.setModelOrderList(modelOrderList);
        assert modelOrderList.getPlacementList() == placementList;
        assert placementList.getModelOrderList() == modelOrderList;
    }

    @Test
    void testChangeRelation() {
        PlacementList placementList1 = new PlacementList();
        ModelOrderList modelOrderList1 = new ModelOrderList();
        PlacementList placementList2 = new PlacementList();
        ModelOrderList modelOrderList2 = new ModelOrderList();
        assert placementList1 != placementList2;
        placementList1.setModelOrderList(modelOrderList1);
        placementList2.setModelOrderList(modelOrderList2);

        assert placementList1.getModelOrderList() == modelOrderList1;
        assert modelOrderList1.getPlacementList() == placementList1;
        assert modelOrderList2.getPlacementList() == placementList2;
        assert placementList2.getModelOrderList() == modelOrderList2;
        System.out.println("=== changing the relationship ===");
        placementList1.setModelOrderList(modelOrderList2);
        placementList2.setModelOrderList(modelOrderList1);

        assert placementList1.getModelOrderList() == modelOrderList2;
        assert modelOrderList2.getPlacementList() == placementList1;
        assert placementList2.getModelOrderList() == modelOrderList1;
        assert modelOrderList1.getPlacementList() == placementList2;
    }
}