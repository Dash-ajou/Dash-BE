package io.saim.dash.account.general.coupon.controller;

import io.saim.dash.account.general.coupon.dto.CouponPaymentCodeResponseDTO;
import io.saim.dash.coupon.common.model.CouponPaymentCode;
import io.saim.dash.account.general.coupon.service.CouponPaymentCodeService;
import io.saim.dash.account.general.coupon.util.QrCodeGeneratorUtil;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponPaymentCodeController {

	private final CouponPaymentCodeService couponPaymentCodeService;

	@PostMapping("/{couponId}/generate-qrcode")
	public ResponseEntity<CommonResponseDTO<CouponPaymentCodeResponseDTO>> generatePaymentQRCode(
		@PathVariable Long couponId) {

		try {
			//QR 코드 생성
			String qrCodeUrl = QrCodeGeneratorUtil.generateQRCode(couponId);
			System.out.println("생성된 QR 코드 URL: " + qrCodeUrl);
			CouponPaymentCode paymentCode = couponPaymentCodeService.generatePaymentCode(couponId, qrCodeUrl);

			//DTO 변환
			CouponPaymentCodeResponseDTO responseDTO = new CouponPaymentCodeResponseDTO(
				paymentCode.getQrCodeUrl(),
				paymentCode.getCoupon().getCouponId()
			);

			return ResponseEntity.status(HttpStatus.CREATED).body(
				new CommonResponseDTO<>(
					new VersionResponseDTO("1.0", "1.0"),
					APIStatus.SUCCESS,
					"결제 코드 생성 성공",
					responseDTO
				)
			);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				new CommonResponseDTO<>(
					new VersionResponseDTO("1.0", "1.0"),
					APIStatus.FAILED,
					"결제 코드 생성 중 오류 발생",
					null
				)
			);
		}
	}
}
