package io.saim.dash.account.general.coupon.service;

import org.springframework.stereotype.Service;

import io.saim.dash.account.general.coupon.dto.CouponDetailResponseDTO;
import io.saim.dash.account.general.coupon.model.CouponRegistration;
import io.saim.dash.account.general.coupon.repository.CouponRegistrationRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponDetailService {

	private final CouponRegistrationRepository couponRegistrationRepository;

	public CouponDetailResponseDTO getCouponDetail(Long couponId, Long userId) {
		CouponRegistration registration = couponRegistrationRepository
			.findByCoupon_CouponIdAndMemberId(couponId, userId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND));

		return CouponDetailResponseDTO.builder()
			.couponId(registration.getCoupon().getCouponId())
			.couponName(registration.getCoupon().getProduct().getProductName())
			.partnerName(registration.getCoupon().getGeneralUser().getOwnerName())
			.validUntil(registration.getCoupon().getCreatedDate().plusMonths(3).toString()) // 예시로 유효기간 3개월 설정
			.build();
	}
}
