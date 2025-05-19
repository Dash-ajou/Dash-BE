package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.dto.CouponRegisterResponseDTO;
import io.saim.dash.account.general.coupon.model.Coupon;
import io.saim.dash.account.general.coupon.model.CouponRegistration;
import io.saim.dash.account.general.coupon.repository.CouponRepository;
import io.saim.dash.account.general.coupon.repository.CouponRegistrationRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CouponRegisterService {

	private final CouponRepository couponRepository;
	private final CouponRegistrationRepository couponRegistrationRepository;

	public CouponRegisterResponseDTO registerCoupon(String couponNumber, Long userId) {

		// 1. 쿠폰 존재 여부 확인
		Coupon coupon = couponRepository.findByCouponNumber(couponNumber)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND));

		// 2. 이미 등록된 쿠폰인지 확인
		if (couponRegistrationRepository.existsByCoupon(coupon)) {
			throw new ServiceException(ServiceExceptionContent.ALREADY_REGISTERED);
		}

		// 3. 등록 수행
		CouponRegistration registration = CouponRegistration.builder()
			.coupon(coupon)
			.memberId(userId)
			.registeredDate(LocalDate.now())
			.build();

		couponRegistrationRepository.save(registration);

		return CouponRegisterResponseDTO.builder()
			.couponId(coupon.getCouponId())
			.couponName(coupon.getProduct().getProductName())
			.partnerName(coupon.getGeneralUser().getOwnerName())
			.validUntil("2025-12-31") // 필요 시 필드로 교체
			.build();
	}
}
