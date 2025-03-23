package io.saim.dash.coupon.common.dto;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VendorDTO {
	private final String vendor_name;

	@Nullable
	private final String president_name;

	@Nullable
	private final String president_phone;

	public String getVendorName() {
		return vendor_name;
	}

	public String getPresidentName() {
		return president_name;
	}

	public String getPresidentPhone() {
		return president_phone;
	}
}
