package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the ProductAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAccountRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

}
