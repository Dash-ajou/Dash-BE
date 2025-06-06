package io.saim.dash.account.general.coupon.service;

import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentCode;
import io.saim.dash.coupon.common.repository.jpa.CouponPaymentCodeJpaRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponPaymentCodeService {

	private final CouponPaymentCodeJpaRepository couponPaymentCodeJpaRepository;
	private final CouponRepository couponRepository;

	@Transactional
	public CouponPaymentCode generatePaymentCode(Long couponId) {
		Coupon coupon = couponRepository.findWithProductAndPartnerById(couponId)
			.orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다: " + couponId));

		Optional<CouponPaymentCode> existingPaymentCode = couponPaymentCodeJpaRepository.findByCoupon_CouponId(couponId);
		if (existingPaymentCode.isPresent()) {
			return existingPaymentCode.get();
		}

		String generatedCode = UUID.randomUUID().toString();

		CouponPaymentCode paymentCode = CouponPaymentCode.builder()
			.coupon(coupon)
			.paymentCode(generatedCode)
			.qrCodeUrl(null)
			.issuedAt(LocalDateTime.now())
			.expiresAt(LocalDateTime.now().plusMinutes(10))
			.build();

		return couponPaymentCodeJpaRepository.save(paymentCode);
	}
}
