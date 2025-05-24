package io.saim.dash.account.general.coupon.controller;

import io.saim.dash.account.general.coupon.dto.CouponResponseDTO;
import io.saim.dash.account.general.coupon.service.CouponService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponController {

	private final CouponService couponService;

	@GetMapping
	public ResponseEntity<CommonResponseDTO<List<CouponResponseDTO>>> getCoupons(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long generalUserId = userDetails.getGeneralUser().getId();
		List<CouponResponseDTO> coupons = couponService.getCouponsByUser(generalUserId);

		return ResponseEntity.ok(new CommonResponseDTO<>(
			null,
			null,
			APIStatus.SUCCESS,
			"쿠폰 목록 조회 성공",
			coupons
		));
	}

	//토큰에서 partnerId 추출하는 메서드 (구현 필요)
	private Long extractPartnerIdFromToken(String token) {
		return 1L; //임시로 1번 파트너 ID 반환
	}
}
