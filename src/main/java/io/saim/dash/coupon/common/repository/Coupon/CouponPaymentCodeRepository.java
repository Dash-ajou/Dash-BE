package io.saim.dash.coupon.common.repository.Coupon;

import java.util.Optional;

import io.saim.dash.coupon.common.model.CouponPaymentCode;

public interface CouponPaymentCodeRepository {

	Optional<CouponPaymentCode> findByPaymentCode(String paymentCode);

	Optional<CouponPaymentCode> findByCoupon_CouponId(Long couponId);
	boolean existsByCoupon_CouponId(Long couponId);
}
