package ai.techfin.tradesystem.repository;

import ai.techfin.tradesystem.domain.XtpAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XtpAccountRepository extends JpaRepository<XtpAccount, String> {
}
