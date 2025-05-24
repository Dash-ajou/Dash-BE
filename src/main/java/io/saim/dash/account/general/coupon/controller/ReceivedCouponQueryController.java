package io.saim.dash.account.general.coupon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.general.coupon.dto.ReceivedCouponDTO;
import io.saim.dash.account.general.coupon.service.ReceivedCouponQueryService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

//받은 쿠폰 목록 조회 API
@RestController
@RequiredArgsConstructor
@RequestMapping("/general/coupons")
public class ReceivedCouponQueryController {

	private final ReceivedCouponQueryService receivedCouponQueryService;

	@GetMapping("/received")
	public ResponseEntity<CommonResponseDTO<List<ReceivedCouponDTO>>> getReceivedCoupons(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null || userDetails.getGeneralUser() == null) {
			throw new ServiceException(ServiceExceptionContent.UNAUTHORIZED);
		}

		Long userId = userDetails.getGeneralUser().getId();
		List<ReceivedCouponDTO> result = receivedCouponQueryService.getReceivedCoupons(userId);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"받은 쿠폰 목록 조회 성공",
				result
			)
		);
	}
}
