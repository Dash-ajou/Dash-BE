package io.saim.dash.account.general.coupon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.general.coupon.dto.SentCouponResponseDTO;
import io.saim.dash.account.general.coupon.service.SentCouponQueryService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class SentCouponQueryController {

	private final SentCouponQueryService sentCouponQueryService;

	@GetMapping("/sent")
	public ResponseEntity<CommonResponseDTO<List<SentCouponResponseDTO>>> getSentCoupons(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		if (userDetails == null || userDetails.getGeneralUser() == null) {
			throw new ServiceException(ServiceExceptionContent.UNAUTHORIZED);
		}

		Long senderId = userDetails.getGeneralUser().getId();
		List<SentCouponResponseDTO> sentCoupons = sentCouponQueryService.getSentCoupons(senderId);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"보낸 쿠폰 목록 조회 성공",
				sentCoupons
			)
		);
	}
}
