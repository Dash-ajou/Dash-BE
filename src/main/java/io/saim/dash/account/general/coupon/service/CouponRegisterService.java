package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.dto.CouponRegisterResponseDTO;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.coupon.common.model.Coupon;

import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponRegisterService {

	private final GeneralUserRepository generalUserRepository;
	private final CouponRepository couponRepository;
	private final CouponRegistrationRepository couponRegistrationRepository;

	public CouponRegisterResponseDTO registerCoupon(String couponNumber, Long userId) {

		// 1. 쿠폰 존재 여부 확인
		Coupon coupon = couponRepository.findByRegistrationCode(couponNumber)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND));

		// 2. 이미 등록된 쿠폰인지 확인
		if (couponRegistrationRepository.existsByCoupon(coupon)) {
			throw new ServiceException(ServiceExceptionContent.ALREADY_REGISTERED);
		}

		// 3. 등록 수행
		GeneralUser registerUser = generalUserRepository.findById(userId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));

		CouponRegistration registration = CouponRegistration.builder()
			.coupon(coupon)
			.registeredUser(registerUser)
			.build();

		couponRegistrationRepository.save(registration);

		return CouponRegisterResponseDTO.builder()
			.couponId(coupon.getCouponId())
			.couponName(coupon.getProduct().getProductName())
			.partnerName(coupon.getIssue().getRequest().getPartner().getPartnerName())
			.validUntil("2025-12-31") // 필요 시 필드로 교체
			.build();
	}
}
