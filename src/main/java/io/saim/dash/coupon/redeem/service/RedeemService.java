package io.saim.dash.coupon.redeem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.dto.Coupon.CouponUseResult;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPayment;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.repository.CouponPayment.CouponPaymentRepository;
import io.saim.dash.coupon.common.repository.CouponPayment.CouponPaymentLogRepository;
import io.saim.dash.coupon.common.repository.Issue.IssueRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedeemService {

	// private final IssueRepository issueRepository;
	// private final CouponPaymentRepository couponPaymentRepository;
	// private final CouponPaymentLogRepository couponPaymentLogRepository;
	//
	// @Transactional
	// public CouponUseResult useCoupon(DUMMY_ServiceUser user, String paymentCode) {
	// 	if (!user.isPartner())
	// 		throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);
	//
	// 	CouponPayment couponPayment = getCouponPayment(paymentCode);
	// 	Coupon coupon = couponPayment.getCoupon();
	// 	Issue issue = coupon.getIssue();
	//
	// 	if (!isValidPartner())
	// 		throw new ServiceException();
	//
	// 	coupon.setCouponStatus(CouponStatus.USED);
	// 	issue.increaseUsedCnt();
	// 	logCouponPayment(couponPayment);
	//
	// }
	//
	// private CouponPayment getCouponPayment(String paymentCode) {
	// 	if (!isPaymentCodeValid(paymentCode))
	// 		throw new ServiceException(ServiceExceptionContent.INVALID_PAYMENT_CODE);
	//
	// 	CouponPayment couponPayment = couponPaymentRepository.findByPaymentCode(paymentCode);
	// 	Coupon coupon = couponPayment.getCoupon();
	// 	Issue issue = coupon.getIssue();
	//
	// 	if (!isIssueStatusValid(issue))
	// 		throw new ServiceException(ServiceExceptionContent.INVALID_ISSUE_STATUS);
	//
	// 	if (!isCouponUsable(coupon))
	// 		throw new ServiceException(ServiceExceptionContent.INVALID_COUPON_STATUS);
	//
	// 	return couponPayment;
	// }
	//
	// private boolean isPaymentCodeValid(String paymentCode) {
	//
	// 	// 형식체크
	//
	// 	// 만료여부 체크
	//
	// 	// 기 사용여부 체크
	//
	// 	return true;
	// }
}
