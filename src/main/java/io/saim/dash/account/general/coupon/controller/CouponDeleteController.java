package io.saim.dash.account.general.coupon.controller;

import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.account.general.coupon.service.CouponDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponDeleteController {

	private final CouponDeleteService couponDeleteService;

	@DeleteMapping("/{couponId}")
	public ResponseEntity<CommonResponseDTO<?>> deleteCoupon(@PathVariable Long couponId) {
		couponDeleteService.deleteCoupon(couponId);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "default"),
				APIStatus.SUCCESS,
				"쿠폰이 성공적으로 삭제되었습니다.",
				new Object() {
					public final Long coupon_id = couponId;
				}
			)
		);
	}
}
