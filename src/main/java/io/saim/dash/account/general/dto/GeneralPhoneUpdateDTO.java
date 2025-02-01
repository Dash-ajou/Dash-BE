package io.saim.dash.account.general.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralPhoneUpdateDTO {
	@JsonProperty("general_new_phone")
	private String generalNewPhone;

	@JsonProperty("general_verify_code")
	private String generalVerifyCode;
}
