package io.saim.dash.coupon.common.repository.jpa;

import io.saim.dash.coupon.common.model.CouponPaymentCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponPaymentCodeJpaRepository extends JpaRepository<CouponPaymentCode, Long> {
	Optional<CouponPaymentCode> findByCoupon_CouponId(Long couponId);

	Optional<CouponPaymentCode> findByPaymentCode(String paymentCode);
}
