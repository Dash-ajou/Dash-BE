package io.saim.dash.account.partner.dto;

import io.saim.dash.account.partner.model.PartnerUser;
import lombok.Getter;

@Getter
public class PartnerAutocompleteDTO {

	private Long partner_id;
	private String partner_name;

	public PartnerAutocompleteDTO(PartnerUser user) {
		this.partner_id = user.getId();
		this.partner_name = user.getPartnerName();
	}
}
