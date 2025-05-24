package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.dto.CouponTransferResponseDTO;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.account.general.coupon.model.CouponDelivery;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.account.general.coupon.repository.CouponDeliveryRepository;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponTransferService {

	private final CouponRepository couponRepository;
	private final CouponRegistrationRepository couponRegistrationRepository;
	private final CouponDeliveryRepository couponDeliveryRepository;
	private final GeneralUserRepository signupNameRepository;

	@Transactional
	public CouponTransferResponseDTO transferCoupon(Long couponId, String receiverEmail, Long senderId) {

		if (receiverEmail == null || receiverEmail.trim().isEmpty()) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);
		}

		String normalizedReceiverEmail = receiverEmail.trim().toLowerCase();

		// 1. 쿠폰 존재 여부 확인
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND));

		// 2. senderId가 소유자인지 확인
		CouponRegistration registration = couponRegistrationRepository
			.findByCouponIdAndMemberId(couponId, senderId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.COUPON_TRANSFER_NOT_ALLOWED));

		// 3. 수신자 확인
		GeneralUser receiver = signupNameRepository.findByEmailIgnoreCase(normalizedReceiverEmail)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));

		// 4. 쿠폰 상태 확인
		if (coupon.getCouponStatus() != CouponStatus.USABLE) {
			throw new ServiceException(ServiceExceptionContent.COUPON_ALREADY_USED);
		}

		// 5. 소유권 이전
		registration.setRegisteredUser(receiver);
		couponRegistrationRepository.save(registration);

		// 6. 전달 기록 저장
		CouponDelivery delivery = CouponDelivery.builder()
			.coupon(coupon)
			.senderId(senderId)
			.receiverId(receiver.getId())
			.requestedAt(LocalDateTime.now())
			.status(CouponDelivery.DeliveryStatus.COMPLETED)
			.build();
		couponDeliveryRepository.save(delivery);

		// 7. 응답 반환
		return CouponTransferResponseDTO.builder()
			.receiverEmail(receiverEmail)
			.couponName(coupon.getProduct().getProductName())
			.build();
	}
}
