package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.TradeSystemApp;
import ai.techfin.tradesystem.domain.ProductAccount;
import ai.techfin.tradesystem.repository.ProductAccountRepository;
import ai.techfin.tradesystem.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static ai.techfin.tradesystem.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductAccountResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = TradeSystemApp.class)
public class ProductAccountResourceIT {

    private static final String DEFAULT_PRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_INITIAL_ASSET = new BigDecimal(0);
    private static final BigDecimal UPDATED_INITIAL_ASSET = new BigDecimal(1);
    private static final BigDecimal SMALLER_INITIAL_ASSET = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_TOTAL_ASSET = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_ASSET = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_ASSET = new BigDecimal(0 - 1);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_AT = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER = "BBBBBBBBBB";

    @Autowired
    private ProductAccountRepository productAccountRepository;

    /**
     * This repository is mocked in the ai.techfin.tradesystem.repository.search test package.
     *
     * @see ai.techfin.tradesystem.repository.search.ProductAccountSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductAccountSearchRepository mockProductAccountSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProductAccountMockMvc;

    private ProductAccount productAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductAccountResource productAccountResource = new ProductAccountResource(productAccountRepository);
        this.restProductAccountMockMvc = MockMvcBuilders.standaloneSetup(productAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAccount createEntity(EntityManager em) {
        ProductAccount productAccount = new ProductAccount()
            .product(DEFAULT_PRODUCT)
            .initialAsset(DEFAULT_INITIAL_ASSET)
            .totalAsset(DEFAULT_TOTAL_ASSET)
            .createdAt(DEFAULT_CREATED_AT)
            .provider(DEFAULT_PROVIDER);
        return productAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAccount createUpdatedEntity(EntityManager em) {
        ProductAccount productAccount = new ProductAccount()
            .product(UPDATED_PRODUCT)
            .initialAsset(UPDATED_INITIAL_ASSET)
            .totalAsset(UPDATED_TOTAL_ASSET)
            .createdAt(UPDATED_CREATED_AT)
            .provider(UPDATED_PROVIDER);
        return productAccount;
    }

    @BeforeEach
    public void initTest() {
        productAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAccount() throws Exception {
        int databaseSizeBeforeCreate = productAccountRepository.findAll().size();

        // Create the ProductAccount
        restProductAccountMockMvc.perform(post("/api/product-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAccount)))
            .andExpect(status().isCreated());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAccount testProductAccount = productAccountList.get(productAccountList.size() - 1);
        assertThat(testProductAccount.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testProductAccount.getInitialAsset()).isEqualTo(DEFAULT_INITIAL_ASSET);
        assertThat(testProductAccount.getTotalAsset()).isEqualTo(DEFAULT_TOTAL_ASSET);
        assertThat(testProductAccount.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProductAccount.getProvider()).isEqualTo(DEFAULT_PROVIDER);

        // Validate the ProductAccount in Elasticsearch
        verify(mockProductAccountSearchRepository, times(1)).save(testProductAccount);
    }

    @Test
    @Transactional
    public void createProductAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAccountRepository.findAll().size();

        // Create the ProductAccount with an existing ID
        productAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAccountMockMvc.perform(post("/api/product-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAccount)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductAccount in Elasticsearch
        verify(mockProductAccountSearchRepository, times(0)).save(productAccount);
    }


    @Test
    @Transactional
    public void checkProductIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAccountRepository.findAll().size();
        // set the field null
        productAccount.setProduct(null);

        // Create the ProductAccount, which fails.

        restProductAccountMockMvc.perform(post("/api/product-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAccount)))
            .andExpect(status().isBadRequest());

        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInitialAssetIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAccountRepository.findAll().size();
        // set the field null
        productAccount.setInitialAsset(null);

        // Create the ProductAccount, which fails.

        restProductAccountMockMvc.perform(post("/api/product-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAccount)))
            .andExpect(status().isBadRequest());

        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalAssetIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAccountRepository.findAll().size();
        // set the field null
        productAccount.setTotalAsset(null);

        // Create the ProductAccount, which fails.

        restProductAccountMockMvc.perform(post("/api/product-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAccount)))
            .andExpect(status().isBadRequest());

        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAccountRepository.findAll().size();
        // set the field null
        productAccount.setCreatedAt(null);

        // Create the ProductAccount, which fails.

        restProductAccountMockMvc.perform(post("/api/product-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAccount)))
            .andExpect(status().isBadRequest());

        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProviderIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAccountRepository.findAll().size();
        // set the field null
        productAccount.setProvider(null);

        // Create the ProductAccount, which fails.

        restProductAccountMockMvc.perform(post("/api/product-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAccount)))
            .andExpect(status().isBadRequest());

        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductAccounts() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        // Get all the productAccountList
        restProductAccountMockMvc.perform(get("/api/product-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT.toString())))
            .andExpect(jsonPath("$.[*].initialAsset").value(hasItem(DEFAULT_INITIAL_ASSET.intValue())))
            .andExpect(jsonPath("$.[*].totalAsset").value(hasItem(DEFAULT_TOTAL_ASSET.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER.toString())));
    }
    
    @Test
    @Transactional
    public void getProductAccount() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        // Get the productAccount
        restProductAccountMockMvc.perform(get("/api/product-accounts/{id}", productAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productAccount.getId().intValue()))
            .andExpect(jsonPath("$.product").value(DEFAULT_PRODUCT.toString()))
            .andExpect(jsonPath("$.initialAsset").value(DEFAULT_INITIAL_ASSET.intValue()))
            .andExpect(jsonPath("$.totalAsset").value(DEFAULT_TOTAL_ASSET.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductAccount() throws Exception {
        // Get the productAccount
        restProductAccountMockMvc.perform(get("/api/product-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAccount() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();

        // Update the productAccount
        ProductAccount updatedProductAccount = productAccountRepository.findById(productAccount.getId()).get();
        // Disconnect from session so that the updates on updatedProductAccount are not directly saved in db
        em.detach(updatedProductAccount);
        updatedProductAccount
            .product(UPDATED_PRODUCT)
            .initialAsset(UPDATED_INITIAL_ASSET)
            .totalAsset(UPDATED_TOTAL_ASSET)
            .createdAt(UPDATED_CREATED_AT)
            .provider(UPDATED_PROVIDER);

        restProductAccountMockMvc.perform(put("/api/product-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductAccount)))
            .andExpect(status().isOk());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
        ProductAccount testProductAccount = productAccountList.get(productAccountList.size() - 1);
        assertThat(testProductAccount.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testProductAccount.getInitialAsset()).isEqualTo(UPDATED_INITIAL_ASSET);
        assertThat(testProductAccount.getTotalAsset()).isEqualTo(UPDATED_TOTAL_ASSET);
        assertThat(testProductAccount.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProductAccount.getProvider()).isEqualTo(UPDATED_PROVIDER);

        // Validate the ProductAccount in Elasticsearch
        verify(mockProductAccountSearchRepository, times(1)).save(testProductAccount);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAccount() throws Exception {
        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();

        // Create the ProductAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAccountMockMvc.perform(put("/api/product-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAccount)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductAccount in Elasticsearch
        verify(mockProductAccountSearchRepository, times(0)).save(productAccount);
    }

    @Test
    @Transactional
    public void deleteProductAccount() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        int databaseSizeBeforeDelete = productAccountRepository.findAll().size();

        // Delete the productAccount
        restProductAccountMockMvc.perform(delete("/api/product-accounts/{id}", productAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductAccount in Elasticsearch
        verify(mockProductAccountSearchRepository, times(1)).deleteById(productAccount.getId());
    }

    @Test
    @Transactional
    public void searchProductAccount() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);
        when(mockProductAccountSearchRepository.search(queryStringQuery("id:" + productAccount.getId())))
            .thenReturn(Collections.singletonList(productAccount));
        // Search the productAccount
        restProductAccountMockMvc.perform(get("/api/_search/product-accounts?query=id:" + productAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT)))
            .andExpect(jsonPath("$.[*].initialAsset").value(hasItem(DEFAULT_INITIAL_ASSET.intValue())))
            .andExpect(jsonPath("$.[*].totalAsset").value(hasItem(DEFAULT_TOTAL_ASSET.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAccount.class);
        ProductAccount productAccount1 = new ProductAccount();
        productAccount1.setId(1L);
        ProductAccount productAccount2 = new ProductAccount();
        productAccount2.setId(productAccount1.getId());
        assertThat(productAccount1).isEqualTo(productAccount2);
        productAccount2.setId(2L);
        assertThat(productAccount1).isNotEqualTo(productAccount2);
        productAccount1.setId(null);
        assertThat(productAccount1).isNotEqualTo(productAccount2);
    }
}
