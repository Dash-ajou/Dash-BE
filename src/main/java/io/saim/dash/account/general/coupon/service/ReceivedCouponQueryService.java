package io.saim.dash.account.general.coupon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.saim.dash.account.general.coupon.dto.ReceivedCouponDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.account.general.coupon.model.CouponDelivery;
import io.saim.dash.account.general.coupon.repository.CouponDeliveryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceivedCouponQueryService {

	private final CouponDeliveryRepository couponDeliveryRepository;

	public List<ReceivedCouponDTO> getReceivedCoupons(Long userId) {
		List<CouponDelivery> deliveries = couponDeliveryRepository.findByReceiverIdAndStatus(userId, CouponDelivery.DeliveryStatus.COMPLETED);

		return deliveries.stream()
			.map(delivery -> {
				Coupon coupon = delivery.getCoupon();
				return ReceivedCouponDTO.builder()
					.couponId(coupon.getCouponId())
					.couponName(coupon.getProduct().getProductName())
					.partnerName(coupon.getIssue().getRequest().getPartner().getOwnerName())  // 또는 vendorName 필드에 따라 조정
					.validUntil(coupon.getCreatedAt().plusMonths(3).toString())  // 예시: 유효기간 3개월
					.couponStatus(coupon.getCouponStatus().name())
					.build();
			})
			.collect(Collectors.toList());
	}
}
