package io.saim.dash.account.partner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.saim.dash.account.partner.model.PartnerUser;
import lombok.Getter;

@Getter
public class PartnerAutocompleteDTO {

	@JsonProperty("partner_id")
	private Long partner_id;

	@JsonProperty("partner_name")
	private String partner_name;

	@JsonProperty("owner_phone")
	private String owner_phone;

	public PartnerAutocompleteDTO(PartnerUser user) {
		this.partner_id = user.getId();
		this.partner_name = user.getPartnerName();
		this.owner_phone = user.getPhone();
	}
}
