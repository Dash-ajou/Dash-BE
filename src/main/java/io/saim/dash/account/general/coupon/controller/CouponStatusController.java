package io.saim.dash.account.general.coupon.controller;

import io.saim.dash.account.general.coupon.service.CouponStatusService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponStatusController {

	private final CouponStatusService couponStatusService;

	@GetMapping("/status")
	public ResponseEntity<CommonResponseDTO<Map<String, Integer>>> getCouponStatus(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long userId = userDetails.getGeneralUser().getId();
		Map<String, Integer> status = couponStatusService.getCouponStatus(userId);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"쿠폰 현황 조회 성공",
				status
			)
		);
	}
}
