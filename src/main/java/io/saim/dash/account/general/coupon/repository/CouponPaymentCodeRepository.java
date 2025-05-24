package io.saim.dash.account.general.coupon.repository;

import io.saim.dash.coupon.common.model.CouponPaymentCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponPaymentCodeRepository extends JpaRepository<CouponPaymentCode, Long> {
	Optional<CouponPaymentCode> findByCoupon_CouponId(Long couponId);
}
