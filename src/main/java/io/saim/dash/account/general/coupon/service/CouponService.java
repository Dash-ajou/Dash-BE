package io.saim.dash.account.general.coupon.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import io.saim.dash.account.general.coupon.dto.CouponResponseDTO;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import io.saim.dash.coupon.common.repository.jpa.CouponJpaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRegistrationRepository couponRegistrationRepository;

	public List<CouponResponseDTO> getCouponsByUser(Long generalUserId) {
		List<CouponRegistration> registrations = couponRegistrationRepository
			.findByRegisteredUserIdAndCoupon_CouponStatus(generalUserId, CouponStatus.USABLE);

		return registrations.stream()
			.filter(reg -> reg.getCoupon().getCouponStatus() != CouponStatus.DISABLED)
			.map(reg -> {
				Coupon coupon = reg.getCoupon();
				return CouponResponseDTO.builder()
					.couponId(coupon.getCouponId())
					.couponName(coupon.getProduct().getProductName())
					.partnerName(coupon.getProduct().getPartner().getPartnerName())
					.validUntil(coupon.getExpiredAt().toString())
					.build();
			})
			.collect(Collectors.toList());
	}
}
