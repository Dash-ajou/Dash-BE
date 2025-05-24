package io.saim.dash.coupon.redeem.dto;

import io.saim.dash.coupon.common.constant.RedeemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedeemLogResponse {
	private final Long          redeemId;
	private final String        paymentCode;
	private final String        usedAt;
	private final RedeemStatus status;
}
