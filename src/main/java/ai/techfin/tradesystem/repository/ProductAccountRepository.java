package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.domain.ProductAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the ProductAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAccountRepository extends JpaRepository<ProductAccount, Long> {

    Optional<ProductAccount> findByName(String name);

}
