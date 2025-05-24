package io.saim.dash.coupon.redeem.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.coupon.redeem.dto.CouponUseCancelRequestDTO;
import io.saim.dash.coupon.redeem.dto.CouponUseCancelResponseDTO;
import io.saim.dash.coupon.redeem.dto.CouponUseResponseDTO;
import io.saim.dash.coupon.redeem.dto.CouponValidateRequestDTO;
import io.saim.dash.coupon.redeem.dto.CouponValidateResponseDTO;
import io.saim.dash.coupon.redeem.dto.RedeemLogResponse;
import io.saim.dash.coupon.redeem.service.ImageService;
import io.saim.dash.coupon.redeem.service.RedeemService;
import io.saim.dash.global.dto.PagingResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/redeem")
public class RedeemController {

	private final RedeemService redeemService;
	private final ImageService imageService;


	@GetMapping("/list")
	public PagingResponse<RedeemLogResponse> listLogs(
		@AuthenticationPrincipal ServiceUser user,
		@RequestParam(value = "redeem_id",  required = false) Long   redeemId,
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
		@AuthenticationPrincipal ServiceUser user,
		@RequestPart("scan_img") MultipartFile scanImage,
		@RequestPart("payment_code") String paymentCode
	) {
		// imageService.saveScanImage(scanImage);
		// CouponUseResult couponUseResult = redeemService.useCoupon(user, paymentCode);

		// return new CouponUseResponseDTO(couponUseResult);
		// return new CouponUseResponseDTO();
		return null;
	}

	@PostMapping("/cancel")
	public CouponUseCancelResponseDTO useCancelCoupon(
		@AuthenticationPrincipal ServiceUser user,
		@RequestBody CouponUseCancelRequestDTO request
	) {
		return new CouponUseCancelResponseDTO();
	}

	@PostMapping("/validate")
	public CouponValidateResponseDTO validateCoupon(
		@AuthenticationPrincipal ServiceUser user,
		@RequestBody CouponValidateRequestDTO request
	) {
		return new CouponValidateResponseDTO();
	}
}
