package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.Product;
import ai.techfin.tradesystem.repository.ProductAccountRepository;
import ai.techfin.tradesystem.security.AuthoritiesConstants;
import ai.techfin.tradesystem.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link Product}.
 */
@RestController
@RequestMapping("/api")
public class ProductAccountResource {

    private static final String ENTITY_NAME = "productAccount";

    private final Logger log = LoggerFactory.getLogger(ProductAccountResource.class);

    private final ProductAccountRepository productAccountRepository;

    @Value("${spring.application.name}")
    private String applicationName;

    public ProductAccountResource(ProductAccountRepository productAccountRepository) {
        this.productAccountRepository = productAccountRepository;
    }

    /**
     * {@code POST  /product-accounts} : Create a new productAccount.
     *
     * @param product the productAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAccount, or
     * with status {@code 400 (Bad Request)} if the productAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-accounts")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Product> createProductAccount(@Valid @RequestBody Product product)
        throws URISyntaxException {
        log.debug("REST request to save ProductAccount : {}", product);
        if (product.getId() != null) {
            throw new BadRequestAlertException("A new productAccount cannot already have an ID", ENTITY_NAME,
                                               "idexists");
        }
        Product result = productAccountRepository.save(product);
        return ResponseEntity.created(new URI("/api/product-accounts/" + result.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-accounts/:id} : get the "id" productAccount.
     *
     * @param id the id of the productAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAccount, or with
     * status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-accounts/{id}")
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.MODEL})
    public ResponseEntity<Product> getProductAccount(@PathVariable Long id) {
        log.debug("REST request to get ProductAccount : {}", id);
        Optional<Product> productAccount = productAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productAccount);
    }

    @GetMapping("/product-accounts/ids")
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.MODEL})
    public List<Long> getProductAccountIds() {
        log.debug("REST request to get ProductAccount ids");
        return productAccountRepository.findAll()
            .stream()
            .map(Product::getId)
            .collect(Collectors.toList());
    }

    /**
     * {@code GET  /product-accounts} : get all the productAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAccounts in body.
     */
    @GetMapping("/product-accounts")
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.MODEL})
    public List<Product> getAllProductAccounts() {
        log.debug("REST request to get all ProductAccounts");
        return productAccountRepository.findAll();
    }

}
