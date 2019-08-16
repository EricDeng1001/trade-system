package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.domain.ModelOrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelOrderListRepository extends JpaRepository<ModelOrderList, Long> {

}
