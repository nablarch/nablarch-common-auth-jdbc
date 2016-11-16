package nablarch.common.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *　SYSTEM_ACCOUNT_AUTHORITY
 */
@Entity
@Table(name = "SYSTEM_ACCOUNT_AUTHORITY")
public class SystemAccountAuthority {
    
    public SystemAccountAuthority() {
    }
    
	public SystemAccountAuthority(String userId, String permissionUnitId) {
		this.userId = userId;
		this.permissionUnitId = permissionUnitId;
	}

    @Id
    @Column(name = "USER_ID", length = 10, nullable = false)
    public String userId;
	
	@Id
    @Column(name = "PERMISSION_UNIT_ID", length = 10, nullable = false)
    public String permissionUnitId;


}
