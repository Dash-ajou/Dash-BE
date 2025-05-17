package io.saim.dash.account.general.coupon.repository;

import io.saim.dash.account.general.coupon.model.Coupon;
import io.saim.dash.account.general.coupon.model.CouponRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRegistrationRepository extends JpaRepository<CouponRegistration, Long> {
	Optional<CouponRegistration> findByCoupon_CouponIdAndMemberId(Long couponId, Long memberId);
	boolean existsByCoupon(Coupon coupon);
	List<CouponRegistration> findByMemberId(Long memberId);
}
