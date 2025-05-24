package io.saim.dash.coupon.redeem.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.coupon.common.model.RedeemLog;
import io.saim.dash.coupon.common.repository.CouponPayment.CouponPaymentRepository;
import io.saim.dash.coupon.common.repository.CouponPayment.CouponPaymentLogRepository;
import io.saim.dash.coupon.common.repository.Issue.IssueRepository;
import io.saim.dash.coupon.redeem.dto.RedeemLogResponse;
import io.saim.dash.global.dto.PagingResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedeemService {

	private final IssueRepository issueRepository;
	private final CouponPaymentRepository couponPaymentRepository;
	private final CouponPaymentLogRepository couponPaymentLogRepository;

	private static final DateTimeFormatter FORMATTER =
		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public PagingResponse<RedeemLogResponse> getLogs(
		ServiceUser user,
		Long redeemId,
		String userName,
		int page,
		int size
	) {
		// TODO: user 권한 검증

		return null;

		// PageRequest paging = PageRequest.of(page - 1, size);
		// Page<RedeemLog> logs = couponPaymentLogRepository.findLogs(redeemId, userName, paging);
		//
		// List<RedeemLogResponse> dtos = logs.stream()
		// 	.map(log -> new RedeemLogResponse(
		// 		log.getRedeemId(),
		// 		log.getPayment().getCode(),
		// 		log.getUsedAt().format(FORMATTER),
		// 		log.getStatus()
		// 	))
		// 	.collect(Collectors.toList());
		//
		// return new PagingResponse<>(page, size, dtos);
	}

	// @Transactional
	// public CouponUseResult useCoupon(ServiceUser user, String paymentCode) {
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

	private boolean isPaymentCodeValid(String paymentCode) {

		// 형식체크

		// 만료여부 체크

		// 기 사용여부 체크

		return true;
	}
}
