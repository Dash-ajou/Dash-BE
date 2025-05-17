package io.saim.dash.account.partner.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.partner.dto.CouponStatsResponseDTO;
import io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO;
import io.saim.dash.account.partner.service.CouponStatsService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/partner")
@RequiredArgsConstructor
public class CouponStatsController {

	private final CouponStatsService couponStatsService;

	@GetMapping("/stats")
	public ResponseEntity<CommonResponseDTO<CouponStatsResponseDTO>> getStats(@AuthenticationPrincipal CustomUserDetails userDetails) {
		Long partnerId = userDetails.getPartnerUser().getId();

		CouponStatsResponseDTO responseDTO = couponStatsService.getPartnerCouponStats(partnerId);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"쿠폰 통계 데이터를 성공적으로 가져왔습니다.",
				responseDTO
			)
		);
	}

	@GetMapping("/stats/detailed")
	public ResponseEntity<CommonResponseDTO<List<CouponVendorDetailStatsDTO>>> getDetailedStats(@AuthenticationPrincipal CustomUserDetails userDetails) {
		Long partnerId = userDetails.getPartnerUser().getId();

		List<CouponVendorDetailStatsDTO> details = couponStatsService.getDetailedStats(partnerId);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"쿠폰 세부 통계 데이터를 성공적으로 가져왔습니다.",
				details
			)
		);
	}
}
