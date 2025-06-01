package io.saim.dash.coupon.payment.dto;

import io.saim.dash.coupon.common.constant.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentLogResponse {
	private final Long          redeemId;
	private final String        paymentCode;
	private final String        usedAt;
	private final PaymentStatus status;
}
