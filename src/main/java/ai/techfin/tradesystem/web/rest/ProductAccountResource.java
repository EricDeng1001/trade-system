package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.ProductAccount;
import ai.techfin.tradesystem.repository.ProductAccountRepository;
import ai.techfin.tradesystem.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link ai.techfin.tradesystem.domain.ProductAccount}.
 */
@RestController
@RequestMapping("/api")
public class ProductAccountResource {

    private final Logger log = LoggerFactory.getLogger(ProductAccountResource.class);

    private static final String ENTITY_NAME = "productAccount";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ProductAccountRepository productAccountRepository;

    public ProductAccountResource(ProductAccountRepository productAccountRepository) {
        this.productAccountRepository = productAccountRepository;
    }

    /**
     * {@code POST  /product-accounts} : Create a new productAccount.
     *
     * @param productAccount the productAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAccount, or with status {@code 400 (Bad Request)} if the productAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-accounts")
    public ResponseEntity<ProductAccount> createProductAccount(@Valid @RequestBody ProductAccount productAccount) throws URISyntaxException {
        log.debug("REST request to save ProductAccount : {}", productAccount);
        if (productAccount.getId() != null) {
            throw new BadRequestAlertException("A new productAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAccount result = productAccountRepository.save(productAccount);
        return ResponseEntity.created(new URI("/api/product-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-accounts} : Updates an existing productAccount.
     *
     * @param productAccount the productAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAccount,
     * or with status {@code 400 (Bad Request)} if the productAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-accounts")
    public ResponseEntity<ProductAccount> updateProductAccount(@Valid @RequestBody ProductAccount productAccount) throws URISyntaxException {
        log.debug("REST request to update ProductAccount : {}", productAccount);
        if (productAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAccount result = productAccountRepository.save(productAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-accounts} : get all the productAccounts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAccounts in body.
     */
    @GetMapping("/product-accounts")
    public List<ProductAccount> getAllProductAccounts() {
        log.debug("REST request to get all ProductAccounts");
        return productAccountRepository.findAll();
    }

    /**
     * {@code GET  /product-accounts/:id} : get the "id" productAccount.
     *
     * @param id the id of the productAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-accounts/{id}")
    public ResponseEntity<ProductAccount> getProductAccount(@PathVariable Long id) {
        log.debug("REST request to get ProductAccount : {}", id);
        Optional<ProductAccount> productAccount = productAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productAccount);
    }

    /**
     * {@code DELETE  /product-accounts/:id} : delete the "id" productAccount.
     *
     * @param id the id of the productAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-accounts/{id}")
    public ResponseEntity<Void> deleteProductAccount(@PathVariable Long id) {
        log.debug("REST request to delete ProductAccount : {}", id);
        productAccountRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
