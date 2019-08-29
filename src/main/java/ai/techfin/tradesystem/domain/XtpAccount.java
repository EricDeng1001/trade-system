package ai.techfin.tradesystem.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "xtp_account")
public class XtpAccount {

    private static final Logger log = LoggerFactory.getLogger(XtpAccount.class);

    @Column(name = "account")
    private String account;

    @Column(name = "trade_key")
    private String tradeKey;

    @Column(name = "password")
    private String password;

    public XtpAccount() {
    }

    public String getAccount() { return account; }

    public void setAccount(String account) { this.account = account; }

    public String getTradeKey() { return tradeKey; }

    public void setTradeKey(String tradeKey) { this.tradeKey = tradeKey; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

}
