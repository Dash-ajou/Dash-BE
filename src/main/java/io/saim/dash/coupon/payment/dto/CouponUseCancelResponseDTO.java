package io.saim.dash.coupon.payment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.saim.dash.coupon.common.model.CouponPaymentLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CouponUseCancelResponseDTO {
	private Boolean result;
	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime usedAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime canceledAt;

	public CouponUseCancelResponseDTO(CouponPaymentLog couponPaymentLog) {
		this.result = true;
		this.id = couponPaymentLog.getPaymentId();
		this.usedAt = couponPaymentLog.getUsedAt();
		this.canceledAt = couponPaymentLog.getCanceledAt();
	}
}
