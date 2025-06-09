package io.saim.dash.account.general.coupon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.saim.dash.account.general.coupon.dto.UsedCouponResponseDTO;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeneralCouponService {

	private final CouponRegistrationRepository couponRegistrationRepository;

	@Transactional(readOnly = true)
	public List<UsedCouponResponseDTO> getUsedCouponsByGeneralUser(Long generalUserId) {
		List<CouponRegistration> registrations =
			couponRegistrationRepository.findUsedByUserId(generalUserId); // 쿠폰 상태가 USED이고 유저가 등록한 것만 조회

		return registrations.stream()
			.map(reg -> {
				Coupon coupon = reg.getCoupon();
				return UsedCouponResponseDTO.builder()
					.couponId(coupon.getCouponId())
					.couponName(coupon.getProduct().getProductName())
					.paymentCode(coupon.getPaymentCode().getPaymentCode()) // null 가능성 주의
					.paymentId(coupon.getPaymentCode().getPaymentLog().getPaymentId())
					.partnerName(coupon.getIssue().getPartner().getPartnerName())
					.usedAt(coupon.getPaymentCode().getPaymentLog().getUsedAt())
					.build();
			})
			.collect(Collectors.toList());
	}
}
