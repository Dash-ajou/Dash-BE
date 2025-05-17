package io.saim.dash.account.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CouponVendorDetailStatsDTO {
	private Long vendorId;
	private String vendorName;
	private Long vendorIssued;
	private Long vendorUsed;

	public double getVendorUsageRate() {
		if (vendorIssued == null || vendorIssued == 0L) return 0.0;
		return Math.round((vendorUsed * 1000.0 / vendorIssued)) / 10.0; // 소수점 첫째 자리
	}
}
