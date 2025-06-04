package io.saim.dash.account.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VendorRawStatsDTO {
	private Long vendorId;
	private String vendorName;
	private Long issuedCount;
	private Long usedCount;
}
