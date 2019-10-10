package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.TestUtil;
import ai.techfin.tradesystem.TradeSystemApp;
import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.Product;
import ai.techfin.tradesystem.domain.enums.BrokerType;
import ai.techfin.tradesystem.repository.PlacementListRepository;
import ai.techfin.tradesystem.repository.ProductAccountRepository;
import ai.techfin.tradesystem.service.ModelOrderService;
import ai.techfin.tradesystem.service.TradeService;
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
    private ModelOrderService modelOrderListRepository;

    @Autowired
    private ProductAccountRepository productAccountRepository;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private PlacementListRepository placementListRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc =
            MockMvcBuilders
                .standaloneSetup(new PlacementController(modelOrderListRepository, tradeService,
                                                         placementListRepository))
                .build();
    }

    @Test
    void canCreatePlacementWhenMOLIsValid() throws Exception {
        ModelOrderList modelOrderList = new ModelOrderList();
        Product product = new Product();
        product.setInitialAsset(new BigDecimal(1));
        product.setTotalAsset(new BigDecimal(1));
        product.setName("TestProductAccount");
        product.setProvider(BrokerType.INTERNAL_SIM);
        productAccountRepository.save(product);
        modelOrderList.setModel("model");
        modelOrderList.setOrders(Collections.emptySet());
        modelOrderList.setProduct(product);
        //modelOrderListRepository.save(modelOrderList);
        PlacementListVM requestBody = new PlacementListVM();
        requestBody.setModelOrderListId(modelOrderList.getId());
        requestBody.setPlacements(Collections.emptySet());
        mockMvc.perform(post("/api/placement-list")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(TestUtil.convertObjectToJsonBytes(requestBody))
        ).andDo(print()).andExpect(status().isCreated());
    }

}
