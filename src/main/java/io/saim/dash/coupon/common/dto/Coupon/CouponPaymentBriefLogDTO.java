package io.saim.dash.coupon.common.dto.Coupon;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponPaymentBriefLogDTO {
	private final Long redeem_id;
	private final String payment_code;
	private final String used_at;
}
