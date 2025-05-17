package io.saim.dash.account.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CouponVendorDetailStatsDTO {
	private String vendorName;
	private Long vendorIssued;
	private Long vendorUsed;
}
