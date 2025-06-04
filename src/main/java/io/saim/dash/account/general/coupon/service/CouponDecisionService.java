package io.saim.dash.account.general.coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.saim.dash.account.general.coupon.model.CouponDelivery;
import io.saim.dash.account.general.coupon.repository.CouponDeliveryRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponDecisionService {

	private final CouponDeliveryRepository couponDeliveryRepository;
	private final CouponRegistrationRepository couponRegistrationRepository;

	@Transactional
	public void processCouponDecision(Long couponId, String decision, Long userId) {

		CouponDelivery delivery = couponDeliveryRepository.findLatestByCouponIdAndReceiverId(couponId, userId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND));

		// 이미 처리된 건 거부
		if (delivery.getStatus() != CouponDelivery.DeliveryStatus.PENDING) {
			throw new ServiceException(ServiceExceptionContent.ALREADY_PROCESSED);
		}

		switch (decision) {
			case "ACCEPT" -> {
				delivery.setStatus(CouponDelivery.DeliveryStatus.COMPLETED);
			}
			case "REJECT" -> {
				delivery.setStatus(CouponDelivery.DeliveryStatus.REJECTED);
			}
			default -> throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);
		}

		couponDeliveryRepository.save(delivery);
	}
}
