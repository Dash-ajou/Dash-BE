package io.saim.dash.coupon.payment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.coupon.common.constant.PaymentStatus;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class PaymentLogBriefResponseDTO {
	private Long  payment_id;
	private String payment_code;
	private PaymentStatus status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime used_at;

	public PaymentLogBriefResponseDTO(CouponPaymentLog couponPaymentLog) {
		this.payment_id = couponPaymentLog.getPaymentId();;
		this.payment_code = couponPaymentLog.getPaymentCode().getPaymentCode();
		this.used_at = couponPaymentLog.getUsedAt();
		this.status = couponPaymentLog.getStatus();
	}
}
