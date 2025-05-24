package io.saim.dash.account.general.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralEmailVerifyConfirmDTO {

	@JsonProperty("new_email")
	private String newEmail;

	@JsonProperty("email_verify_code")
	private String emailVerifyCode;
}
