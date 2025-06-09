package io.saim.dash.account.general.coupon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.saim.dash.account.general.annotation.LoginGeneralUser;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.coupon.dto.UsedCouponResponseDTO;
import io.saim.dash.account.general.coupon.service.GeneralCouponService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class GeneralCouponController {

	private final GeneralCouponService generalCouponService;

	@GetMapping("/used")
	public ResponseEntity<CommonResponseDTO<List<UsedCouponResponseDTO>>> getUsedCoupons(
		@LoginGeneralUser GeneralUser user,
		@RequestParam(name = "partnerId", required = false) Long partnerId
	) {
		List<UsedCouponResponseDTO> usedCoupons = generalCouponService.getUsedCoupons(user, partnerId);
		return ResponseEntity.ok(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"사용 완료 쿠폰 목록 조회 성공",
			usedCoupons
		));
	}
}
