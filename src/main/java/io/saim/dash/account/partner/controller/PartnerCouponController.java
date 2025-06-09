package io.saim.dash.account.partner.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import io.saim.dash.account.general.coupon.dto.UsedCouponResponseDTO;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.service.PartnerCouponService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/partner/coupons")
@RequiredArgsConstructor
public class PartnerCouponController {

	private final PartnerCouponService partnerCouponService;

	@GetMapping("/list/used")
	public ResponseEntity<CommonResponseDTO<List<UsedCouponResponseDTO>>> getUsedCoupons(HttpSession session) {
		PartnerUser partnerUser = (PartnerUser) session.getAttribute("partner_user");

		if (partnerUser == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "파트너 로그인 필요");
		}

		List<UsedCouponResponseDTO> usedCoupons = partnerCouponService.getUsedCouponsByPartner(partnerUser.getId());

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"사용 완료 쿠폰 목록 조회 성공",
				usedCoupons
			)
		);
	}
}
