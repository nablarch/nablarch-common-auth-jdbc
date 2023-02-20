package nablarch.common.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * SYSTEM_ACCOUNT
 */
@Entity
@Table(name = "SYSTEM_ACCOUNT")
public class SystemAccount {
    
    public SystemAccount() {
    }
    
    public SystemAccount(String userId, String password, String userIdLocked,
			String passwordExpirationDate, Long failedCount,
			String effectiveDateFrom, String effectiveDateTo) {
		this.userId = userId;
		this.password = password;
		this.userIdLocked = userIdLocked;
		this.passwordExpirationDate = passwordExpirationDate;
		this.failedCount = failedCount;
		this.effectiveDateFrom = effectiveDateFrom;
		this.effectiveDateTo = effectiveDateTo;
	}

	@Id
    @Column(name = "USER_ID", length = 10, nullable = false)
    public String userId;
    
    @Column(name = "PASSWORD", length = 128, nullable = false)
    public String password;

    @Column(name = "USER_ID_LOCKED", length = 1, nullable = false)
    public String userIdLocked = "0";
    
    @Column(name = "PASSWORD_EXPIRATION_DATE", length = 8, nullable = false)
    public String passwordExpirationDate = "99991231";
    
    @Column(name = "FAILED_COUNT", length = 1, nullable = false)
    public Long failedCount = 0L;
    
    @Column(name = "EFFECTIVE_DATE_FROM", length = 8, nullable = false)
    public String effectiveDateFrom = "19000101";

    @Column(name = "EFFECTIVE_DATE_TO", length = 8, nullable = false)
    public String effectiveDateTo = "99991231";
}
