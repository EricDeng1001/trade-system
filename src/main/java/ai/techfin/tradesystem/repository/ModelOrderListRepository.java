package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.ProductAccount;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ModelOrderListRepository extends JpaRepository<ModelOrderList, Long> {

    List<ModelOrderList> findByCreatedAtBetweenAndProductAccount(Instant begin, Instant end, ProductAccount product);

}
