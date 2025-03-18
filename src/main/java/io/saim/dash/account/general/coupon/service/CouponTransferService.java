package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.dto.CouponTransferResponseDTO;
import io.saim.dash.account.general.coupon.model.Coupon;
import io.saim.dash.account.general.coupon.model.CouponDelivery;
import io.saim.dash.account.general.coupon.model.CouponRegistration;
import io.saim.dash.account.general.coupon.repository.CouponRegistrationRepository;
import io.saim.dash.account.general.coupon.repository.CouponRepository;
import io.saim.dash.account.general.coupon.repository.CouponDeliveryRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponTransferService {

	private final CouponRepository couponRepository;
	private final CouponRegistrationRepository couponRegistrationRepository;
	private final CouponDeliveryRepository couponDeliveryRepository;
	private final SignupNameRepository signupNameRepository;

	@Transactional
	public CouponTransferResponseDTO transferCoupon(Long couponId, String receiverEmail) {

		//receiverEmail이 null 또는 빈 값인지 체크
		if (receiverEmail == null || receiverEmail.trim().isEmpty()) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);
		}

		//현재 로그인한 사용자의 general_id 가져오기
		Long senderId = getCurrentUserId();

		String normalizedReceiverEmail = receiverEmail.trim().toLowerCase();

		//쿠폰 존재 여부 확인
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> {
				return new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND);
			});

		//senderId가 쿠폰 소유자인지 확인
		CouponRegistration registration = couponRegistrationRepository.findByCoupon_CouponIdAndMemberId(couponId, senderId)
			.orElseThrow(() -> {
				return new ServiceException(ServiceExceptionContent.COUPON_TRANSFER_NOT_ALLOWED);
			});

		//receiverEmail에 해당하는 general_id 조회
		GeneralUser receiver = signupNameRepository.findByGeneralEmailIgnoreCase(normalizedReceiverEmail)
			.orElseThrow(() -> {
				return new ServiceException(ServiceExceptionContent.USER_NOT_FOUND);
			});

		// 사용된 쿠폰인지 확인
		if (coupon.getCouponStatus() != Coupon.CouponStatus.ACTIVE) {
			throw new ServiceException(ServiceExceptionContent.COUPON_ALREADY_USED);
		}

		//쿠폰 소유권 이전
		registration.setMemberId(receiver.getGeneralId());
		couponRegistrationRepository.save(registration);

		//쿠폰 전달 기록 저장
		CouponDelivery delivery = CouponDelivery.builder()
			.coupon(coupon)
			.senderId(senderId) //기존 소유자
			.receiverId(receiver.getGeneralId()) //새로운 소유자
			.requestedAt(LocalDateTime.now())
			.status(CouponDelivery.DeliveryStatus.COMPLETED)
			.build();
		couponDeliveryRepository.save(delivery);

		return CouponTransferResponseDTO.builder()
			.receiverEmail(receiverEmail)
			.couponName(coupon.getProduct().getProductName())
			.build();
	}

	//현재 로그인한 사용자의 general_id를 가져오는 메서드
	private Long getCurrentUserId() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		if (principal instanceof GeneralUser generalUser) {
			return generalUser.getGeneralId();
		} else if (principal instanceof UserDetails userDetails) {
			String userInput = userDetails.getUsername();

			//userInput이 이메일인지 전화번호인지 판별
			boolean isEmail = userInput.contains("@");

			if (!isEmail) {
				//이메일이 아니라면 전화번호로 general_id 찾기
				GeneralUser user = signupNameRepository.findByGeneralPhone(userInput.trim())
					.orElseThrow(() -> {
						return new ServiceException(ServiceExceptionContent.USER_NOT_FOUND);
					});

				return user.getGeneralId();
			}

			return signupNameRepository.findByGeneralEmailIgnoreCase(userInput.trim())
				.orElseThrow(() -> {
					return new ServiceException(ServiceExceptionContent.USER_NOT_FOUND);
				})
				.getGeneralId();
		} else {
			throw new ServiceException(ServiceExceptionContent.UNAUTHORIZED);
		}
	}
}
