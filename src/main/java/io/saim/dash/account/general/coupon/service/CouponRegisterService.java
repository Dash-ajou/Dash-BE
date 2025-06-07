package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.dto.CouponRegisterResponseDTO;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.jpa.CouponJpaRepository;
import io.saim.dash.coupon.common.repository.jpa.IssueJpaRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponRegisterService {

	private final GeneralUserRepository generalUserRepository;
	private final CouponRepository couponRepository;
	private final CouponRegistrationRepository couponRegistrationRepository;
	private final CouponJpaRepository couponJpaRepository;
	private final IssueJpaRepository issueJpaRepository;

	public CouponRegisterResponseDTO registerCoupon(String couponNumber, Long userId) {
		// 1. 쿠폰 존재 여부 확인
		Coupon coupon = couponRepository.findByRegistrationCode(couponNumber);

		if (coupon.getCouponStatus() != CouponStatus.REGISTERABLE) {
			throw new ServiceException(ServiceExceptionContent.INVALID_COUPON_STATUS);
		}

		// 2. 이미 등록된 쿠폰인지 확인
		if (couponRegistrationRepository.existsByCoupon(coupon)) {
			throw new ServiceException(ServiceExceptionContent.ALREADY_REGISTERED);
		}

		// 3. 등록 수행
		GeneralUser registerUser = generalUserRepository.findById(userId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));

		CouponRegistration registration = CouponRegistration.builder()
			.coupon(coupon)
			.registeredUser(registerUser)
			.build();

		couponRegistrationRepository.save(registration);

		coupon.setCouponStatus(CouponStatus.USABLE);
		couponJpaRepository.save(coupon);

		coupon.getIssue().increaseRegisterCnt();
		issueJpaRepository.save(coupon.getIssue());

		return CouponRegisterResponseDTO.builder()
			.couponId(coupon.getCouponId())
			.couponName(coupon.getProduct().getProductName())
			.partnerName(coupon.getIssue().getRequest().getPartner().getPartnerName())
			.validUntil("2025-12-31") // 필요 시 expiredAt 필드로 교체 가능
			.build();
	}
}
