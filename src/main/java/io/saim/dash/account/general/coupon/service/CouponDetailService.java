package io.saim.dash.account.general.coupon.service;

import org.springframework.stereotype.Service;

import io.saim.dash.account.general.coupon.dto.CouponDetailResponseDTO;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponDetailService {

	private final CouponRegistrationRepository couponRegistrationRepository;

	public CouponDetailResponseDTO getCouponDetail(Long couponId, Long userId) {
		CouponRegistration registration = couponRegistrationRepository
			.findByCouponIdAndMemberId(couponId, userId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND));

		return CouponDetailResponseDTO.builder()
			.couponId(registration.getCoupon().getCouponId())
			.couponName(registration.getCoupon().getProduct().getProductName())
			.partnerName(registration.getCoupon().getIssue().getRequest().getPartner().getOwnerName())
			.validUntil(registration.getCoupon().getExpiredAt().toString()) // 예시로 유효기간 3개월 설정
			.build();
	}
}
