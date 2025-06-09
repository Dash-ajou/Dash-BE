package io.saim.dash.account.general.coupon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.saim.dash.account.general.coupon.dto.UsedCouponResponseDTO;
import io.saim.dash.account.general.coupon.service.GeneralCouponService;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.global.annotation.LoginPartnerUser;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class GeneralCouponController {

	private final GeneralCouponService generalCouponService;

	@GetMapping("/used")
	public ResponseEntity<CommonResponseDTO<List<UsedCouponResponseDTO>>> getUsedCoupons(
		@LoginPartnerUser PartnerUser loginPartnerUser
	) {
		List<UsedCouponResponseDTO> usedCoupons = generalCouponService.getUsedCouponsByPartner(loginPartnerUser.getId());

		return ResponseEntity.ok(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"사용 완료 쿠폰 목록 조회 성공",
			usedCoupons
		));
	}
}
