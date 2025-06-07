package io.saim.dash.coupon.common.repository.Coupon;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.common.model.CouponPaymentCode;
import io.saim.dash.coupon.common.repository.jpa.CouponPaymentCodeJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponPaymentCodeRepositoryImpl implements CouponPaymentCodeRepository {

	private final CouponPaymentCodeJpaRepository couponPaymentCodeJpaRepository;

	@Override
	public Optional<CouponPaymentCode> findByPaymentCode(String paymentCode) {
		return couponPaymentCodeJpaRepository.findByPaymentCode(paymentCode);
	}

	@Override
	public Optional<CouponPaymentCode> findByCoupon_CouponId(Long couponId) {
		return couponPaymentCodeJpaRepository.findByCoupon_CouponId(couponId);
	}

	@Override
	public boolean existsByCoupon_CouponId(Long couponId) {
		return couponPaymentCodeJpaRepository.existsByCoupon_CouponId(couponId);
	}
}
