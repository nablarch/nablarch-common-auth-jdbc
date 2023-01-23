package nablarch.common.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
