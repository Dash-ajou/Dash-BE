package io.saim.dash.coupon.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;

public interface CouponRegistrationJpaRepository extends JpaRepository<CouponRegistration, Long> {
	boolean existsByCoupon(Coupon coupon);
}
