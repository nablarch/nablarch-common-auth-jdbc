package nablarch.common.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PERMISSION_UNIT
 */
@Entity
@Table(name = "PERMISSION_UNIT")
public class PermissionUnit {
    
    public PermissionUnit() {
    }
    
	public PermissionUnit(String permissionUnitId) {
		this.permissionUnitId = permissionUnitId;
	}

	@Id
    @Column(name = "PERMISSION_UNIT_ID", length = 10, nullable = false)
    public String permissionUnitId;
}
