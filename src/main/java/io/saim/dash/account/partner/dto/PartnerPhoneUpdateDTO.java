package io.saim.dash.account.partner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerPhoneUpdateDTO {

	@JsonProperty("owner_new_phone")
	private String ownerNewPhone;

	@JsonProperty("owner_verify_code")
	private String ownerVerifyCode;
}
