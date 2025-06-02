package io.saim.dash.coupon.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CouponUseRequestDTO {
	private String payment_code;

	public String getPaymentCode() {
		return payment_code;
	}
}
