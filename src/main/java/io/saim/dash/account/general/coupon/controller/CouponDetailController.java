package io.saim.dash.account.general.coupon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.general.coupon.dto.CouponDetailResponseDTO;
import io.saim.dash.account.general.coupon.service.CouponDetailService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/general/coupons")
public class CouponDetailController {

	private final CouponDetailService couponDetailService;

	@GetMapping("/{couponId}")
	public ResponseEntity<CommonResponseDTO<CouponDetailResponseDTO>> getCouponDetail(
		@PathVariable Long couponId,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long userId = userDetails.getGeneralUser().getId();

		CouponDetailResponseDTO response = couponDetailService.getCouponDetail(couponId, userId);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"쿠폰 상세 조회 성공",
				response
			)
		);
	}
}
