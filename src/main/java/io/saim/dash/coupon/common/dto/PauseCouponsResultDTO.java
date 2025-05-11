package io.saim.dash.coupon.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class PauseCouponsResultDTO {
	private final Long issueCount;
	private final Long updatedCount;
}
