package io.saim.dash.account.general.coupon.controller;

import io.saim.dash.account.general.coupon.dto.CouponResponseDTO;
import io.saim.dash.account.general.coupon.service.CouponService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponController {

	private final CouponService couponService;

	@GetMapping
	public ResponseEntity<CommonResponseDTO<List<CouponResponseDTO>>> getCoupons(HttpSession session) {
		Long generalUserId = (Long) session.getAttribute("user_id");

		if (generalUserId == null) {
			return ResponseEntity.status(401).body(new CommonResponseDTO<>(
				null,
				null,
				APIStatus.FAILED,
				"로그인이 필요합니다.",
				null
			));
		}

		List<CouponResponseDTO> coupons = couponService.getCouponsByUser(generalUserId);

		return ResponseEntity.ok(new CommonResponseDTO<>(
			null,
			null,
			APIStatus.SUCCESS,
			"쿠폰 목록 조회 성공",
			coupons
		));
	}

	private Long extractPartnerIdFromToken(String token) {
		return 1L;
	}
}
