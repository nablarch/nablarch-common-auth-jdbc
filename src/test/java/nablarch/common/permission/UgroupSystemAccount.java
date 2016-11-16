package nablarch.common.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
