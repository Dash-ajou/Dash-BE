package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.model.Coupon;
import io.saim.dash.account.general.coupon.model.Coupon.CouponStatus;
import io.saim.dash.account.general.coupon.repository.CouponRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponStatusService {

	private final CouponRegistrationRepository couponRegistrationRepository;

	public Map<String, Integer> getCouponStatus(Long userId) {
		int usable = couponRegistrationRepository.countByMemberIdAndCoupon_CouponStatus(userId, Coupon.CouponStatus.ACTIVE);
		int used = couponRegistrationRepository.countByMemberIdAndCoupon_CouponStatus(userId, Coupon.CouponStatus.USED);
		Map<String, Integer> result = new HashMap<>();
		result.put("usable_count", usable);
		result.put("used_count", used);
		return result;
	}
}
