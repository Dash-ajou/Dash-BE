package io.saim.dash.coupon.payment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.saim.dash.coupon.common.constant.PaymentStatus;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentLogBriefResponseDTO {
	private Long  payment_id;
	private String payment_code;
	private PaymentStatus status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime usedAt;

	public PaymentLogBriefResponseDTO(CouponPaymentLog couponPaymentLog) {
		this.payment_id = couponPaymentLog.getPaymentId();;
		this.payment_code = couponPaymentLog.getPaymentCode().getPaymentCode();
		this.usedAt = couponPaymentLog.getUsedAt();
		this.status = couponPaymentLog.getStatus();
	}
}
