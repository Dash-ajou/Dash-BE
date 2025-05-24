package io.saim.dash.account.general.coupon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.saim.dash.account.general.coupon.dto.SentCouponResponseDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.account.general.coupon.model.CouponDelivery;
import io.saim.dash.account.general.coupon.repository.CouponDeliveryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SentCouponQueryService {

	private final CouponDeliveryRepository couponDeliveryRepository;

	@Transactional(readOnly = true)
	public List<SentCouponResponseDTO> getSentCoupons(Long senderId) {
		List<CouponDelivery> deliveries = couponDeliveryRepository.findBySenderId(senderId);

		return deliveries.stream()
			.map(delivery -> {
				Coupon coupon = delivery.getCoupon();
				return SentCouponResponseDTO.builder()
					.couponId(coupon.getCouponId())
					.couponName(coupon.getProduct().getProductName())
					.partnerName(coupon.getIssue().getRequest().getPartner().getPartnerName())
					.validUntil(coupon.getExpiredAt().toString())
					.couponStatus(delivery.getStatus().name())
					.build();
			})
			.collect(Collectors.toList());
	}
}
