package io.saim.dash.account.general.coupon.controller;

import io.saim.dash.account.general.coupon.dto.*;
import io.saim.dash.account.general.coupon.service.CouponRegisterService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.exception.ServiceExceptionContent;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponRegisterController {

	private final CouponRegisterService couponRegisterService;

	@PostMapping("/register")
	public ResponseEntity<CommonResponseDTO<CouponRegisterResponseDTO>> registerCoupon(
		@RequestBody CouponRegisterRequestDTO requestDTO,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		if (requestDTO == null || requestDTO.getCouponNumber() == null || requestDTO.getCouponNumber().trim().isEmpty()) {
			throw new io.saim.dash.global.exception.ServiceException(ServiceExceptionContent.INVALID_INPUT);
		}

		Long generalUserId = userDetails.getGeneralUser().getId();
		CouponRegisterResponseDTO responseDTO = couponRegisterService.registerCoupon(requestDTO.getCouponNumber(), generalUserId);

		return ResponseEntity.status(201).body(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"쿠폰이 성공적으로 등록되었습니다.",
				responseDTO
			)
		);
	}
}
