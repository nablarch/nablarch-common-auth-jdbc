package nablarch.common.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *　UGROUP_AUTHORITY
 */
@Entity
@Table(name = "UGROUP_AUTHORITY")
public class UgroupAuthority {
    
    public UgroupAuthority() {
    }
    
	public UgroupAuthority(String ugroupId, String permissionUnitId) {
		this.ugroupId = ugroupId;
		this.permissionUnitId = permissionUnitId;
	}

    @Id
    @Column(name = "UGROUP_ID", length = 10, nullable = false)
    public String ugroupId;
	
	@Id
    @Column(name = "PERMISSION_UNIT_ID", length = 10, nullable = false)
    public String permissionUnitId;


}
