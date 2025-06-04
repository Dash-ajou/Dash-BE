package io.saim.dash.account.push.dto;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;

public record UserSearchResultDTO(
	PartnerUser partnerUser, GeneralUser generalUser
) {
	public Boolean isPartner() {
		return partnerUser() != null;
	}
}
