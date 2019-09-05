package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.TestUtil;
import ai.techfin.tradesystem.TradeSystemApp;
import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.ProductAccount;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.repository.ProductAccountRepository;
import ai.techfin.tradesystem.service.PlacementService;
import ai.techfin.tradesystem.web.rest.vm.PlacementListVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TradeSystemApp.class)
@EmbeddedKafka
@ActiveProfiles("test")
class PlacementControllerIT {

    private static final Logger log = LoggerFactory.getLogger(PlacementControllerIT.class);

    @Autowired
    private ModelOrderListRepository modelOrderListRepository;

    @Autowired
    private ProductAccountRepository productAccountRepository;

    @Autowired
    private PlacementService placementService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc =
            MockMvcBuilders
                .standaloneSetup(new PlacementController(modelOrderListRepository, placementService))
                .build();
    }

    @Test
    void canCreatePlacementWhenMOLIsValid() throws Exception {
        ModelOrderList modelOrderList = new ModelOrderList();
        ProductAccount productAccount = new ProductAccount();
        productAccount.setInitialAsset(new BigDecimal(1));
        productAccount.setTotalAsset(new BigDecimal(1));
        productAccount.setName("TestProductAccount");
        productAccount.setProvider("provider");
        productAccountRepository.save(productAccount);
        modelOrderList.setModel("model");
        modelOrderList.setOrders(Collections.emptySet());
        modelOrderList.setProductAccount(productAccount);
        modelOrderListRepository.save(modelOrderList);
        PlacementListVM requestBody = new PlacementListVM();
        requestBody.setModelOrderListId(modelOrderList.getId());
        requestBody.setPlacements(Collections.emptySet());
        mockMvc.perform(post("/api/placement-list")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(TestUtil.convertObjectToJsonBytes(requestBody))
        ).andDo(print()).andExpect(status().isCreated());
    }

}
