package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.domain.ProductAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAccountRepository extends JpaRepository<ProductAccount, Long> {

}
