package io.saim.dash.coupon.payment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CouponUseCancelRequestDTO {
	private final String payment_code;
	private final Long redeem_id;
}
