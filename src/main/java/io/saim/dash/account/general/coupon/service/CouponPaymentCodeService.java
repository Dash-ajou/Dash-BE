package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.util.QrCodeGeneratorUtil;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentCode;
import io.saim.dash.coupon.common.repository.jpa.CouponPaymentCodeJpaRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
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
	private final QrCodeGeneratorUtil qrCodeGeneratorUtil;

	@Transactional
	public CouponPaymentCode generatePaymentCode(Long couponId) {
		Coupon coupon = couponRepository.findWithProductAndPartnerById(couponId)
			.orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다: " + couponId));

		if (coupon.getCouponStatus() != CouponStatus.USABLE) {
			throw new ServiceException(ServiceExceptionContent.INVALID_COUPON_STATUS);
		}
		if (coupon.getIssue().getIssueActiveStatus() != IssueActiveStatus.ENABLE) {
			throw new ServiceException(ServiceExceptionContent.INVALID_ISSUE_STATUS);
		}

		Optional<CouponPaymentCode> existingPaymentCode = couponPaymentCodeJpaRepository.findByCoupon_CouponId(couponId);
		if (existingPaymentCode.isPresent()) {
			CouponPaymentCode existing = existingPaymentCode.get();

			if (existing.getExpiresAt().isAfter(LocalDateTime.now())) {
				return existing;
			}

			couponPaymentCodeJpaRepository.clearPaymentCodeReference(existing.getPaymentCodeId());

			couponPaymentCodeJpaRepository.delete(existing);
			couponPaymentCodeJpaRepository.flush();
		}

		String generatedCode = UUID.randomUUID().toString();

		String qrImageBase64;
		try {
			qrImageBase64 = qrCodeGeneratorUtil.generateQRCodeBase64(generatedCode);
		} catch (Exception e) {
			throw new RuntimeException("QR 코드 생성 실패", e);
		}

		CouponPaymentCode paymentCode = CouponPaymentCode.builder()
			.coupon(coupon)
			.paymentCode(generatedCode)
			.qrCodeImage(qrImageBase64)
			.issuedAt(LocalDateTime.now())
			.expiresAt(LocalDateTime.now().plusSeconds(60))
			.build();

		coupon.setPaymentCode(paymentCode);

		return couponPaymentCodeJpaRepository.save(paymentCode);
	}
}
