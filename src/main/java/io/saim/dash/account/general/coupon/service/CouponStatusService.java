package io.saim.dash.account.general.coupon.service;

import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.repository.jpa.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponStatusService {

	private final CouponJpaRepository couponJpaRepository;

	public Map<String, Integer> getCouponStatus(Long userId) {
		List<Coupon> usableCoupons = couponJpaRepository.findByGeneralUser_IdAndCouponStatus(userId, CouponStatus.USABLE);
		List<Coupon> usedCoupons = couponJpaRepository.findByGeneralUser_IdAndCouponStatus(userId, CouponStatus.USED);

		Map<String, Integer> result = new HashMap<>();
		result.put("usable_count", usableCoupons.size());
		result.put("used_count", usedCoupons.size());
		return result;
	}
}
