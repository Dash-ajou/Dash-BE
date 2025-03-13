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

	public List<CouponResponseDTO> getCoupons(Long partnerId) {
		List<Coupon> coupons = couponRepository.findByProduct_Partner_PartnerId(partnerId);
		return coupons.stream()
			.map(coupon -> CouponResponseDTO.builder()
				.couponId(coupon.getCouponId())
				.couponName(coupon.getProduct().getProductName())
				.partnerName(coupon.getProduct().getPartner().getPartnerName())
				.validUntil(coupon.getCreatedDate().plusDays(30).toString()) //예시로 만료일을 30일 후로 설정
				.build())
			.collect(Collectors.toList());
	}
}
