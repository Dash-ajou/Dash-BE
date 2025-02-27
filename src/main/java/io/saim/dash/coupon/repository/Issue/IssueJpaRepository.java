package io.saim.dash.coupon.repository.Issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.model.Issue;

public interface IssueJpaRepository extends JpaRepository<Issue, Long> {
	Page<Issue> findByVendor(Long vendorId, Pageable pageable);
	Page<Issue> findByPartner(Long partnerId, Pageable pageable);
}
