package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.model.Coupon;
import io.saim.dash.account.general.coupon.model.CouponPaymentCode;
import io.saim.dash.account.general.coupon.repository.CouponPaymentCodeRepository;
import io.saim.dash.account.general.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponPaymentCodeService {

	private final CouponPaymentCodeRepository couponPaymentCodeRepository;
	private final CouponRepository couponRepository;

	@Transactional
	public CouponPaymentCode generatePaymentCode(Long couponId, String qrCodeUrl) {
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다: " + couponId));

		//기존 결제 코드가 있다면 재사용
		Optional<CouponPaymentCode> existingPaymentCode = couponPaymentCodeRepository.findByCoupon_CouponId(couponId);
		if (existingPaymentCode.isPresent()) {
			return existingPaymentCode.get();
		}

		//새로운 결제 코드 생성
		CouponPaymentCode paymentCode = CouponPaymentCode.builder()
			.coupon(coupon)
			.qrCodeUrl(qrCodeUrl)
			.issuedAt(LocalDateTime.now())
			.expiresAt(LocalDateTime.now().plusMinutes(10)) //10분 후 만료
			.build();

		return couponPaymentCodeRepository.save(paymentCode);
	}
}
