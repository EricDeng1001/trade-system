package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.domain.HibernateTestBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HibernateTestRepository extends JpaRepository<HibernateTestBase, Long> {
}
