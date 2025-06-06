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
	private final QrCodeGeneratorUtil qrCodeGeneratorUtil;

	@PostMapping("/{couponId}/generate-qrcode")
	public ResponseEntity<CommonResponseDTO<CouponPaymentCodeResponseDTO>> generatePaymentQRCode(
		@PathVariable Long couponId) {

		try {
			String qrCodeBase64 = qrCodeGeneratorUtil.generateQRCodeBase64(couponId);
			CouponPaymentCode paymentCode = couponPaymentCodeService.generatePaymentCode(couponId);

			if (paymentCode == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new CommonResponseDTO<>(
						new VersionResponseDTO("1.0", "1.0"),
						APIStatus.FAILED,
						"결제 코드 생성에 실패하였습니다.",
						null
					)
				);
			}

			CouponPaymentCodeResponseDTO responseDTO = new CouponPaymentCodeResponseDTO(
				qrCodeBase64,
				paymentCode.getCoupon().getCouponId(),
				paymentCode.getPaymentCode()
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
			e.printStackTrace();
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
