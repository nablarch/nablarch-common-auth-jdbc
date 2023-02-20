package nablarch.common.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
