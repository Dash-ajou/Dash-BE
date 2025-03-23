package io.saim.dash.coupon.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PartnerDTO {
	private final String business_name;
	private final String owner_phone;

	public String getBusinessName() {
		return business_name;
	}

	public String getOwnerPhone() {
		return owner_phone;
	}
}
