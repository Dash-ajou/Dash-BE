package io.saim.dash.account.general.coupon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.saim.dash.account.general.coupon.dto.CouponDecisionRequestDTO;
import io.saim.dash.account.general.coupon.service.CouponDecisionService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponDecisionController {

	private final CouponDecisionService couponDecisionService;

	@PatchMapping("/{couponId}/decision")
	public ResponseEntity<CommonResponseDTO<Void>> decideCoupon(
		@PathVariable Long couponId,
		@RequestBody CouponDecisionRequestDTO requestDTO,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {

		if (userDetails == null || userDetails.getGeneralUser() == null) {
			throw new ServiceException(ServiceExceptionContent.UNAUTHORIZED);
		}
		if (requestDTO.getApprovalDecision() == null) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);
		}

		couponDecisionService.processCouponDecision(
			couponId,
			requestDTO.getApprovalDecision().trim().toUpperCase(),
			userDetails.getGeneralUser().getId()
		);

		String message = switch (requestDTO.getApprovalDecision().trim().toUpperCase()) {
			case "ACCEPT" -> "쿠폰이 수락되었습니다.";
			case "REJECT" -> "쿠폰이 거절되었습니다.";
			default -> throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);
		};

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				message,
				null
			)
		);
	}
}
