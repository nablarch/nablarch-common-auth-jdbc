package nablarch.common.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
