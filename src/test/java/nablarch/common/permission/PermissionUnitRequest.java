package nablarch.common.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * PERMISSION_UNIT_REQUEST
 */
@Entity
@Table(name = "PERMISSION_UNIT_REQUEST")
public class PermissionUnitRequest {
    
    public PermissionUnitRequest() {
    }
    
	public PermissionUnitRequest(String permissionUnitId, String requestId) {
		this.permissionUnitId = permissionUnitId;
		this.requestId = requestId;
	}

	@Id
    @Column(name = "PERMISSION_UNIT_ID", length = 10, nullable = false)
    public String permissionUnitId;

    @Id
    @Column(name = "REQUEST_ID", length = 10, nullable = false)
    public String requestId;
}
