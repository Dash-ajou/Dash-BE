package io.saim.dash.account.partner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartnerAccountResponseDTO {

	@JsonProperty("owner_name")
	private String ownerName;

	@JsonProperty("owner_phone")
	private String ownerPhone;

	@JsonProperty("owner_email")
	private String ownerEmail;

	@JsonProperty("is_temporary")
	private String isTemporary;

	@JsonProperty("temporary_register_date")
	private String temporaryRegisterDate;
}
