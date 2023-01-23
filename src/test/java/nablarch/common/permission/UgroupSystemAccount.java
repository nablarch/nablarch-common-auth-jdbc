package nablarch.common.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * UGROUP_SYSTEM_ACCOUNT
 */
@Entity
@Table(name = "UGROUP_SYSTEM_ACCOUNT")
public class UgroupSystemAccount {
    
    public UgroupSystemAccount() {
    }
    
    public UgroupSystemAccount(String ugroupId, String userId,
			String effectiveDateFrom, String effectiveDateTo) {
		this.ugroupId = ugroupId;
		this.userId = userId;
		this.effectiveDateFrom = effectiveDateFrom;
		this.effectiveDateTo = effectiveDateTo;
	}

	@Id
    @Column(name = "UGROUP_ID", length = 10, nullable = false)
    public String ugroupId;

    @Id
    @Column(name = "USER_ID", length = 10, nullable = false)
    public String userId;

    @Id
    @Column(name = "EFFECTIVE_DATE_FROM", length = 8, nullable = false)
    public String effectiveDateFrom = "19000101";

    @Column(name = "EFFECTIVE_DATE_TO", length = 8, nullable = false)
    public String effectiveDateTo = "99991231";
}
