package nablarch.common.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UGROUP
 */
@Entity
@Table(name = "UGROUP")
public class Ugroup {
    
    public Ugroup() {
    }
    
	public Ugroup(String ugroupId) {
		this.ugroupId = ugroupId;
	}

	@Id
    @Column(name = "UGROUP_ID", length = 10, nullable = false)
    public String ugroupId;
}
