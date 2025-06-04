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
	public CouponPaymentCode generatePaymentCode(Long couponId, String qrCodeUrl) {
		Coupon coupon = couponRepository.findWithProductAndPartnerById(couponId)
			.orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다: " + couponId));

		//기존 결제 코드가 있다면 재사용
		Optional<CouponPaymentCode> existingPaymentCode = couponPaymentCodeJpaRepository.findByCoupon_CouponId(couponId);
		if (existingPaymentCode.isPresent()) {
			return existingPaymentCode.get();
		}

		String generatedCode = UUID.randomUUID().toString();
		//새로운 결제 코드 생성
		CouponPaymentCode paymentCode = CouponPaymentCode.builder()
			.coupon(coupon)
			.paymentCode(generatedCode)
			.qrCodeUrl(qrCodeUrl)
			.issuedAt(LocalDateTime.now())
			.expiresAt(LocalDateTime.now().plusMinutes(10)) //10분 후 만료
			.build();

		return couponPaymentCodeJpaRepository.save(paymentCode);
	}
}
