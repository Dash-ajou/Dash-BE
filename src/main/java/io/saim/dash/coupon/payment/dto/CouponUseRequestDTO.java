package io.saim.dash.coupon.payment.dto;

public record CouponUseRequestDTO (
	String payment_code
) {
	public String paymentCode() {
		return payment_code;
	}
}
