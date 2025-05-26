package io.saim.dash.coupon.common.dto;

import io.saim.dash.account.partner.model.PartnerUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter @AllArgsConstructor
public class PartnerDTO {
	private String business_name;
	private String owner_phone;

	public PartnerDTO(PartnerUser partner) {
		this.business_name = partner.getPartnerName();
		this.owner_phone = partner.getPhone();
	}

	public String getBusinessName() {
		return business_name;
	}

	public String getOwnerPhone() {
		return owner_phone;
	}
}
