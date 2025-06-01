package io.saim.dash.coupon.payment.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.coupon.common.constant.CodeType;
import io.saim.dash.coupon.common.dto.Coupon.CouponCodeInfo;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.jpa.CouponPaymentCodeJpaRepository;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.constant.PaymentStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentCode;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponPaymentLogRepository;
import io.saim.dash.coupon.common.repository.Issue.IssueRepository;
import io.saim.dash.coupon.payment.dto.PaymentLogResponse;
import io.saim.dash.global.dto.PagingResponse;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

	private final CouponRepository couponRepository;
	private final PartnerUserRepository partnerUserRepository;
	private final CouponRegistrationRepository couponRegistrationRepository;
	private final CouponPaymentLogRepository couponPaymentLogRepository;
	private final CouponPaymentCodeJpaRepository couponPaymentCodeJpaRepository;

	private static final DateTimeFormatter FORMATTER =
		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final GeneralUserRepository generalUserRepository;

	public PagingResponse<PaymentLogResponse> getLogs(
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

	@Transactional
	public CouponPaymentLog useCoupon(ServiceUser loginUser, String requestedPaymentCode) {
		PartnerUser partnerUser = getPartnerAPIAccessUser(loginUser);

		CouponPaymentCode couponPaymentCode = getCouponPaymentCodeInfo(requestedPaymentCode);
		checkCouponValidation(couponPaymentCode.getCoupon(), partnerUser);
		CouponPaymentLog couponRedeemResult = applyPayment(partnerUser, couponPaymentCode);

		// TODO: 알림 추가

		return couponRedeemResult;
	}

	private PartnerUser getPartnerAPIAccessUser(ServiceUser loginUser) {
		if (!loginUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		return partnerUserRepository.findById(((PartnerUser)loginUser).getId())
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));
	}

	private ServiceUser getAPIAccessUser(ServiceUser loginUser) {
		if (loginUser.isPartner()) return this.getPartnerAPIAccessUser(loginUser);
		return generalUserRepository.findById(((GeneralUser)loginUser).getId())
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));
	}

	private CouponPaymentLog applyPayment(PartnerUser partnerUser, CouponPaymentCode payment) {
		CouponPaymentLog couponPayment = createCouponPaymentLog(partnerUser, payment);
		payment.getCoupon().setCouponStatus(CouponStatus.USED);
		payment.getCoupon().getIssue().increaseUsedCnt();

		return couponPayment;
	}

	private CouponPaymentLog cancelPayment(PartnerUser partnerUser, Long paymentId, CouponPaymentCode payment) {
		CouponPaymentLog couponPaymentLog = getCouponPaymentLog(partnerUser, paymentId);
		couponPaymentLog.setStatus(PaymentStatus.CANCELLED);
		couponPaymentLog.setCanceledAt(LocalDateTime.now());

		payment.getCoupon().setCouponStatus(CouponStatus.USABLE);
		payment.getCoupon().getIssue().decreaseUsedCnt();

		return couponPaymentLog;
	}

	private CouponPaymentLog createCouponPaymentLog(PartnerUser partnerUser, CouponPaymentCode paymentCode) {
		CouponRegistration couponRegistration = couponRegistrationRepository.findByCoupon(paymentCode.getCoupon());
		GeneralUser couponOwner = couponRegistration.getRegisteredUser();

		CouponPaymentLog couponPayment = CouponPaymentLog.builder()
				.paymentCode(paymentCode)
				.member(couponOwner)
				.partner(partnerUser)
				.usedAt(LocalDateTime.now())
				.status(PaymentStatus.USED)
				.build();

		couponPaymentLogRepository.save(couponPayment);
		return couponPayment;
	}

	private boolean isPaymentCodeValid(String paymentCode) {

		// 형식체크

		return true;
	}

	private void checkCouponValidation(Coupon coupon, PartnerUser partnerUser) {
		Issue issue = coupon.getIssue();
		Request request = issue.getRequest();

		if (!request.isRequestedPartner(partnerUser))
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		if (isCouponUsed(coupon))
			throw new ServiceException(ServiceExceptionContent.COUPON_ALREADY_USED);

		if (!isCouponUsable(coupon))
			throw new ServiceException(ServiceExceptionContent.INVALID_COUPON_STATUS);
	}

	private boolean isCouponUsable(Coupon coupon) {
		if (coupon.getCouponStatus() != CouponStatus.USABLE) return false;
		return coupon.getIssue().getIssueActiveStatus() == IssueActiveStatus.ENABLED;
	}

	private boolean isCouponUsed(Coupon coupon) {
		return coupon.getCouponStatus() == CouponStatus.USED;
	}

	private CouponPaymentCode getCouponPaymentCodeInfo(String requestedPaymentCode) {
		if(!isPaymentCodeValid(requestedPaymentCode))
			throw new ServiceException(ServiceExceptionContent.INVALID_PAYMENT_CODE);

		CouponPaymentCode couponPaymentCode = couponPaymentCodeJpaRepository.findByPaymentCode(requestedPaymentCode)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.PAYMENT_CODE_NOT_FOUND));
		// 만료여부 체크
		if (couponPaymentCode.getExpiresAt().isBefore(LocalDateTime.now()))
			throw new ServiceException(ServiceExceptionContent.PAYMENT_CODE_EXPIRED);

		return couponPaymentCode;
	}

	@Transactional
	public CouponPaymentLog cancelCoupon(ServiceUser loginUser, String paymentCode, Long paymentId) {
		PartnerUser partnerUser = getPartnerAPIAccessUser(loginUser);

		CouponPaymentCode couponPaymentCodeInfo = getCouponPaymentCodeInfo(paymentCode);
		CouponPaymentLog paymentLog = cancelPayment(partnerUser, paymentId, couponPaymentCodeInfo);

		// TODO: 알림 추가

		return paymentLog;
	}

	private CouponPaymentLog getCouponPaymentLog(PartnerUser partnerUser, Long paymentId) {
		CouponPaymentLog couponPaymentLog = couponPaymentLogRepository.findById(paymentId);
		if (!couponPaymentLog.getPartner().equals(partnerUser))
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		return couponPaymentLog;
	}

	public CouponCodeInfo validateCoupon(ServiceUser loginUser, String code) {

		CodeType codeType = checkCodeType(code);
		Coupon coupon = getCouponByCode(code, codeType);

		return new CouponCodeInfo(codeType, coupon);
	}

	private Coupon getCouponByCode(String code, CodeType codeType) {
		Coupon coupon;
		if (codeType == CodeType.PAYMENT_CODE) {
			CouponPaymentCode couponPaymentCodeInfo = getCouponPaymentCodeInfo(code);
			coupon = couponPaymentCodeInfo.getCoupon();
		} else {
			coupon = couponRepository.findByRegistrationCode(code);
		}

		if (coupon == null)
			throw new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND);

		return coupon;
	}

	private CodeType checkCodeType(String code) {
		if (isPaymentCodeValid(code)) return CodeType.PAYMENT_CODE;
		return CodeType.REGISTER_CODE;
	}
}
