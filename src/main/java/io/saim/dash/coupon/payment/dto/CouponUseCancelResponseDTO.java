package io.saim.dash.coupon.payment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.coupon.common.model.CouponPaymentLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class CouponUseCancelResponseDTO {
	private Boolean result;
	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime used_at;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime canceled_at;

	public CouponUseCancelResponseDTO(CouponPaymentLog couponPaymentLog) {
		this.result = true;
		this.id = couponPaymentLog.getPaymentId();
		this.used_at = couponPaymentLog.getUsedAt();
		this.canceled_at = couponPaymentLog.getCanceledAt();
	}
}
