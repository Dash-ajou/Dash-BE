package io.saim.dash.account.partner.dto;

public record VendorRawStatsDTO(
	Long vendorId,
	String vendorName,
	Long issuedCount,
	Long usedCount
) {}
