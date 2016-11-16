package nablarch.common.availability;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 申請リクエスト
 */
@Entity
@Table(name = "APPLIED_REQUEST")
public class AppliedRequest {
    
    public AppliedRequest() {
    };
    
    public AppliedRequest(String appliedRequestId, String appliedServiceAvailable) {
        this.appliedRequestId = appliedRequestId;
        this.appliedServiceAvailable = appliedServiceAvailable;
    }

    @Id
    @Column(name = "APPLIED_REQUEST_ID", length = 10, nullable = false)
    public String appliedRequestId;

    @Column(name = "APPLIED_SERVICE_AVAILABLE", length = 1)
    public String appliedServiceAvailable;
}
