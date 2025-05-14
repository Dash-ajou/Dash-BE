package io.saim.dash.coupon.redeem.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.redeem.dto.CouponUseCancelRequestDTO;
import io.saim.dash.coupon.redeem.dto.CouponUseCancelResponseDTO;
import io.saim.dash.coupon.redeem.dto.CouponUseResponseDTO;
import io.saim.dash.coupon.redeem.dto.CouponValidateRequestDTO;
import io.saim.dash.coupon.redeem.dto.CouponValidateResponseDTO;
import io.saim.dash.coupon.redeem.service.ImageService;
import io.saim.dash.coupon.redeem.service.RedeemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/redeem")
public class RedeemController {

	private final RedeemService redeemService;
	private final ImageService imageService;

	@PostMapping("/use")
	public CouponUseResponseDTO useCoupon(
		@AuthenticationPrincipal DUMMY_ServiceUser user,
		@RequestPart("scan_img") MultipartFile scanImage,
		@RequestPart("payment_code") String paymentCode
	) {
		// imageService.saveScanImage(scanImage);
		// CouponUseResult couponUseResult = redeemService.useCoupon(user, paymentCode);

		// return new CouponUseResponseDTO(couponUseResult);
		return new CouponUseResponseDTO();
	}

	@PostMapping("/cancel")
	public CouponUseCancelResponseDTO useCancelCoupon(
		@AuthenticationPrincipal DUMMY_ServiceUser user,
		@RequestBody CouponUseCancelRequestDTO request
	) {
		return new CouponUseCancelResponseDTO();
	}

	@PostMapping("/validate")
	public CouponValidateResponseDTO validateCoupon(
		@AuthenticationPrincipal DUMMY_ServiceUser user,
		@RequestBody CouponValidateRequestDTO request
	) {
		return new CouponValidateResponseDTO();
	}
}
