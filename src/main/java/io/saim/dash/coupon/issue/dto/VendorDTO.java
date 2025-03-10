package io.saim.dash.coupon.issue.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VendorDTO {
	private final String vendor_name;
	private final String president_name;
	private final String president_phone;
}
