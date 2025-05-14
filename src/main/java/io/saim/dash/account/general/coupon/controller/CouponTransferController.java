package io.saim.dash.account.general.coupon.controller;

import io.saim.dash.account.general.coupon.dto.CouponTransferRequestDTO;
import io.saim.dash.account.general.coupon.dto.CouponTransferResponseDTO;
import io.saim.dash.account.general.coupon.service.CouponTransferService;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponTransferController {

	private final CouponTransferService couponTransferService;

	@PostMapping("/{couponId}/transfer")
	public ResponseEntity<CommonResponseDTO<CouponTransferResponseDTO>> transferCoupon(
		@PathVariable Long couponId,
		@RequestHeader(value = "Cookie", required = false) String sessionToken,
		@RequestBody(required = false) CouponTransferRequestDTO requestDTO,
		Principal principal) {

		//JSON 요청이 null이면 에러 발생
		if (requestDTO == null) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);
		}

		//receiverEmail이 null인지 체크
		String receiverEmail = requestDTO.getReceiverEmail();
		if (receiverEmail == null || receiverEmail.trim().isEmpty()) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);
		}

		//현재 로그인한 사용자 ID 가져오기
		if (principal == null || principal.getName() == null) {
			throw new ServiceException(ServiceExceptionContent.UNAUTHORIZED);
		}

		Long senderId;
		try {
			senderId = Long.parseLong(principal.getName());
		} catch (NumberFormatException e) {
			throw new ServiceException(ServiceExceptionContent.UNAUTHORIZED);
		}

		//쿠폰 양도 서비스 호출
		CouponTransferResponseDTO responseDTO = couponTransferService.transferCoupon(couponId, receiverEmail);

		//응답 반환
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
