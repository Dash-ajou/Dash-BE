package io.saim.dash.coupon.redeem.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CouponValidateRequestDTO {
	private final String code;
}
