package io.saim.dash.coupon.payment.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.common.model.UserType;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import io.saim.dash.coupon.payment.dto.CouponUseCancelRequestDTO;
import io.saim.dash.coupon.payment.dto.CouponUseCancelResponseDTO;
import io.saim.dash.coupon.payment.dto.CouponUseRequestDTO;
import io.saim.dash.coupon.payment.dto.CouponUseResponseDTO;
import io.saim.dash.coupon.payment.dto.CouponValidateRequestDTO;
import io.saim.dash.coupon.payment.dto.CouponValidateResponseDTO;
import io.saim.dash.coupon.payment.dto.PaymentLogBriefResponseDTO;
import io.saim.dash.coupon.payment.dto.PaymentLogResponseDTO;
import io.saim.dash.coupon.payment.service.ImageService;
import io.saim.dash.coupon.payment.service.PaymentService;
import io.saim.dash.global.dto.PagingResponse;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/payment")
public class PaymentController {

	private final PaymentService paymentService;
	private final ImageService imageService;

	@GetMapping("/log/list")
	public PagingResponse<PaymentLogBriefResponseDTO> getPaymentLogs(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestParam(value = "payment_id", required = false) Long paymentId,
		@RequestParam(value = "payment_code", required = false) String paymentCode,
		@RequestParam(value = "coupon_id", required = false) Long couponId,
		@RequestParam(value = "user_name",  required = false) String userName,
		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
		@RequestParam(value = "size", required = false, defaultValue = "10") int size
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);
		List<CouponPaymentLog> paymentList = paymentService.getLogs(
			loginUser, page, size,
			paymentId, paymentCode, couponId, userName
		);

		List<PaymentLogBriefResponseDTO> responseDTOs = paymentList.stream()
			.map(PaymentLogBriefResponseDTO::new)
			.toList();
		return new PagingResponse(
			page, size,
			responseDTOs
		);
	}

	@GetMapping("/log/{payment_id}")
	public PaymentLogResponseDTO getPaymentLogSpec(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long paymentId
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);
		CouponPaymentLog couponPaymentLog = paymentService.getLog(loginUser, paymentId);

		return new PaymentLogResponseDTO(couponPaymentLog);
	}

	@Transactional
	@PostMapping("/use")
	public CouponUseResponseDTO useCoupon(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody CouponUseRequestDTO couponUseRequestDTO
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);
		CouponPaymentLog couponPaymentLog = paymentService.useCoupon(
			loginUser, couponUseRequestDTO.getPaymentCode()
		);

		return new CouponUseResponseDTO(couponPaymentLog);
	}

	@Transactional
	@PostMapping("/cancel")
	public CouponUseCancelResponseDTO cancelCoupon(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody CouponUseCancelRequestDTO couponUseCancelRequestDTO
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);
		CouponPaymentLog paymentLog = paymentService.cancelCoupon(
			loginUser,
			couponUseCancelRequestDTO.getPaymentCode(), couponUseCancelRequestDTO.getPaymentId()
		);

		return new CouponUseCancelResponseDTO(paymentLog);
	}

	@Transactional
	@PostMapping("/validate")
	public CouponValidateResponseDTO validateCoupon(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody CouponValidateRequestDTO request
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);
		paymentService.validateCoupon(loginUser, request.getCode());

		return new CouponValidateResponseDTO();
	}

	private static ServiceUser getLoginUser(CustomUserDetails customUserDetails) {
		ServiceUser user;
		try {
			user = customUserDetails.getGeneralUser();
		} catch (Exception e) {
			user = customUserDetails.getPartnerUser();
		}
		user.setUserType(UserType.valueOf(customUserDetails.getUserType()));
		return user;
	}
}
