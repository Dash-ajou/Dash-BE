package io.saim.dash.coupon.payment.dto;

import org.springframework.web.multipart.MultipartFile;

public record CouponUseRequestDTO (
	String payment_code
) {
	public String paymentCode() {
		return payment_code;
	}
}
