package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.domain.PlacementList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacementListRepository extends JpaRepository<PlacementList, Long> {}
