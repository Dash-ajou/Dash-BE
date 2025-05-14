package io.saim.dash.coupon.redeem.dto;

public class CouponUseCancelResponseDTO {
	private final Boolean result;
	private final Long id;
	private final String used_at;
	private final String canceled_at;

	public CouponUseCancelResponseDTO() {
		this.result = true;
		this.id = 67543L;
		this.used_at = "2025-01-06 10:20:05";
		this.canceled_at = "2025-01-06 10:20:05";
	}
}
