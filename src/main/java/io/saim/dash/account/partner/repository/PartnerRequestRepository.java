package io.saim.dash.account.partner.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import io.saim.dash.account.partner.model.PartnerRequest;
import io.saim.dash.coupon.common.constant.IssueStatus;

public interface PartnerRequestRepository extends JpaRepository<PartnerRequest, Long> {

	@Query("SELECT p FROM PartnerRequest p " +
		"WHERE (:partnerName IS NULL OR p.partnerName LIKE %:partnerName%) " +
		"AND (:requestStatus IS NULL OR p.requestStatus = :requestStatus)")
	List<PartnerRequest> searchPartnerRequests(
		@Param("partnerName") String partnerName,
		@Param("requestStatus") IssueStatus requestStatus
	);
}
