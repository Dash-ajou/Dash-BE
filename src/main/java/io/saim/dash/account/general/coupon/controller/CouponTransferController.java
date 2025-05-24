package io.saim.dash.account.general.coupon.controller;

import io.saim.dash.account.general.coupon.dto.CouponTransferRequestDTO;
import io.saim.dash.account.general.coupon.dto.CouponTransferResponseDTO;
import io.saim.dash.account.general.coupon.service.CouponTransferService;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponTransferController {

	private final CouponTransferService couponTransferService;

	@PostMapping("/{couponId}/transfer")
	public ResponseEntity<CommonResponseDTO<CouponTransferResponseDTO>> transferCoupon(
		@PathVariable Long couponId,
		@RequestBody(required = false) CouponTransferRequestDTO requestDTO,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		System.out.println("[Controller] /transfer API 호출됨");

		if (userDetails == null || userDetails.getGeneralUser() == null) {
			System.out.println("userDetails or generalUser is null");
			throw new ServiceException(ServiceExceptionContent.UNAUTHORIZED);
		}

		if (requestDTO == null || requestDTO.getReceiverEmail() == null || requestDTO.getReceiverEmail().trim().isEmpty()) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);
		}

		Long senderId = userDetails.getGeneralUser().getId();
		System.out.println("userDetails.getGeneralUser().getId() = " + senderId);

		CouponTransferResponseDTO responseDTO =
			couponTransferService.transferCoupon(couponId, requestDTO.getReceiverEmail(), senderId);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"쿠폰 양도 성공",
				responseDTO
			)
		);
	}
}
