package io.saim.dash.coupon.payment.dto;

import org.springframework.web.multipart.MultipartFile;

public record CouponUseRequestDTO (
	MultipartFile scan_img,
	String payment_code
) {
	public String paymentCode() {
		return payment_code;
	}

	public MultipartFile scanImg() {
		return scan_img;
	}
}
