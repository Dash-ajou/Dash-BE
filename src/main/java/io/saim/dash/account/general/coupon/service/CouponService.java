package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.dto.CouponResponseDTO;
import io.saim.dash.account.general.coupon.model.Coupon;
import io.saim.dash.account.general.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	public List<CouponResponseDTO> getCouponsByUser(Long generalUserId) {
		// 일반 사용자 ID 기준으로 쿠폰 조회
		List<Coupon> coupons = couponRepository.findByGeneralUser_Id(generalUserId);

		return coupons.stream()
			.map(coupon -> CouponResponseDTO.builder()
				.couponId(coupon.getCouponId())
				.couponName(coupon.getProduct().getProductName())
				.partnerName(coupon.getProduct().getPartner().getPartnerName())
				.validUntil(coupon.getCreatedDate().plusDays(30).toString()) // 예시: 생성일 기준 30일 유효
				.build())
			.collect(Collectors.toList());
	}
}
