package io.saim.dash.coupon.repository.Issue;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.model.VendorGroup;

public interface IssueJpaRepository extends JpaRepository<Issue, Long> {
	@Query("SELECT i FROM Issue i WHERE i.vendorGroup IN :vendors")
	List<Issue> findAllByVendors(
		@Param("vendors") List<VendorGroup> vendors
	);

	@Query("SELECT i FROM Issue i WHERE i.partner == :partner")
	List<Issue> findAllByPartner(
		@Param("partner") DUMMY_PartnerUser partner
	);
}
