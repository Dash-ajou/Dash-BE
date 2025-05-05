package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.Coupon;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
	List<Coupon> findByIssueId(Long issueId, Pageable pageable);
}
