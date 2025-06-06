package io.saim.dash.account.partner.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.saim.dash.account.partner.dto.CouponStatsResponseDTO;
import io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO;
import io.saim.dash.account.partner.dto.OrganizationStatsResponseDTO;
import io.saim.dash.account.partner.service.CouponStatsService;
import io.saim.dash.account.partner.service.OrganizationStatsService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/partner")
@RequiredArgsConstructor
public class CouponStatsController {

	private final CouponStatsService couponStatsService;
	private final OrganizationStatsService organizationStatsService;

	@GetMapping("/stats")
	public ResponseEntity<?> getStats(@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (!"PARTNER".equals(userDetails.getUserType())) {
			return ResponseEntity
				.status(403)
				.body(new CommonResponseDTO<>(
					new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
					APIStatus.FAILED,
					"해당 사용자는 PARTNER 타입이 아닙니다.",
					null
				));
		}

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
	public ResponseEntity<?> getDetailedStats(@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (!"PARTNER".equals(userDetails.getUserType())) {
			return ResponseEntity
				.status(403)
				.body(new CommonResponseDTO<>(
					new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
					APIStatus.FAILED,
					"해당 사용자는 PARTNER 타입이 아닙니다.",
					null
				));
		}

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

	@GetMapping("/stats/organization/{vendor_id}")
	public ResponseEntity<CommonResponseDTO<OrganizationStatsResponseDTO>> getVendorDetails(
		@PathVariable("vendor_id") Long vendorId) {

		OrganizationStatsResponseDTO response = organizationStatsService.getStats(vendorId);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"발급 단체 상세 정보를 성공적으로 가져왔습니다.",
				response
			)
		);
	}
}
