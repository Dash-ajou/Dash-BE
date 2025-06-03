package io.saim.dash.account.partner.dto;

public record VendorRawStatsDTO(
	String vendorName,
	Long issuedCount,
	Long usedCount
) {}
