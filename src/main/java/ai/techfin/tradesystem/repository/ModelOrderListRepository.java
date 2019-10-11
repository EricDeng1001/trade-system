package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ModelOrderListRepository extends JpaRepository<ModelOrderList, Long> {

    List<ModelOrderList> findByCreatedAtBetweenAndProduct(Instant begin, Instant end, Product product);

    List<ModelOrderList> findByModel(String Model);

}
