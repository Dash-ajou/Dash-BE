package io.saim.dash.coupon.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CouponUseRequestDTO {
	private String payment_code;

	public String getPaymentCode() {
		return payment_code;
	}
}
