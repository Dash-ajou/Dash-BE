package io.saim.dash.account.general.coupon.service;

import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponStatusService {

	private final CouponRegistrationRepository couponRegistrationRepository;

	public Map<String, Integer> getCouponStatus(Long userId) {
		int usable = couponRegistrationRepository.findByMemberIdAndCouponStatus(userId, CouponStatus.USABLE).size();
		int used = couponRegistrationRepository.findByMemberIdAndCouponStatus(userId, CouponStatus.USED).size();

		Map<String, Integer> result = new HashMap<>();
		result.put("usable_count", usable);
		result.put("used_count", used);
		return result;
	}
}
