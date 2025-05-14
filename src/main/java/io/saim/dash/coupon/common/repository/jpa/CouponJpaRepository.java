package io.saim.dash.coupon.common.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.Coupon;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
	List<Coupon> findByIssueIssueId(Long issueId);
}
