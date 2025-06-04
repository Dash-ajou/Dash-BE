package io.saim.dash.coupon.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.saim.dash.account.partner.model.PartnerUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter @AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PartnerDTO {
	private String businessName;
	private String ownerPhone;

	public PartnerDTO(PartnerUser partner) {
		this.businessName = partner.getPartnerName();
		this.ownerPhone = partner.getPhone();
	}
}
