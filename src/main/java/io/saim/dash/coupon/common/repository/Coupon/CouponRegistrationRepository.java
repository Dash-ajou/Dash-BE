package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;
import java.util.Optional;

import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;

public interface CouponRegistrationRepository {
	CouponRegistration findByCouponId(Long couponId);

	List<CouponRegistration> findByMemberId(Long memberId);

	Optional<CouponRegistration> findByCouponIdAndMemberId(Long couponId, Long memberId);

	boolean existsByCoupon(Coupon coupon);

	List<CouponRegistration> findByRegisteredUserIdAndIsValid(Long userId, Boolean isValid);

	void save(CouponRegistration couponRegistration);
}

