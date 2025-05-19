package io.saim.dash.account.partner.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CouponStatsResponseDTO {
	private Long totalIssued;
	private Long totalUsed;
	private Long totalRemainder;
	private Integer usageRate;
	private List<CouponVendorDetailStatsDTO> detailedStats;
}
