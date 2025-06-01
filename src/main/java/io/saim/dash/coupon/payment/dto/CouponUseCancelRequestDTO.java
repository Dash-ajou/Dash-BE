package io.saim.dash.coupon.payment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class CouponUseCancelRequestDTO {
	private final String payment_code;
	private final Long payment_id;

	public String getPaymentCode() {
		return payment_code;
	}

	public Long getPaymentId() {
		return payment_id;
	}
}
