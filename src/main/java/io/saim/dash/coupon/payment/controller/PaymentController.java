package io.saim.dash.coupon.payment.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
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
import io.saim.dash.coupon.payment.dto.PaymentLogResponse;
import io.saim.dash.coupon.payment.service.ImageService;
import io.saim.dash.coupon.payment.service.PaymentService;
import io.saim.dash.global.dto.PagingResponse;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/redeem")
public class PaymentController {

	private final PaymentService paymentService;
	private final ImageService imageService;

	@GetMapping("/list")
	public PagingResponse<PaymentLogResponse> listLogs(
		@AuthenticationPrincipal ServiceUser user,
		@RequestParam(value = "request_id",  required = false) Long   redeemId,
		@RequestParam(value = "user_name",  required = false) String userName,
		@RequestParam(value = "page",       required = false, defaultValue = "1")  int page,
		@RequestParam(value = "size",       required = false, defaultValue = "10") int size
	) {
		return null;
		// return redeemService.getLogs(
		// 	user, redeemId, userName, page, size
		// );
	}

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
