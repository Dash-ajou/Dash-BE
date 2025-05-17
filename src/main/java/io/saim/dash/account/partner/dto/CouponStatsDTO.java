package io.saim.dash.account.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CouponStatsDTO {
	private Long totalIssued;
	private Long totalUsed;
	private Long totalRemainder;

	public int getUsageRate() {
		if (totalIssued == null || totalIssued == 0L) return 0;
		return (int) Math.round((double) (totalUsed != null ? totalUsed : 0) / totalIssued * 100);
	}
}
