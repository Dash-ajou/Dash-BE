package io.saim.dash.account.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhoneVerificationCheckDTO {
	@JsonProperty("user_phone")
	private String userPhone;

	@JsonProperty("user_verify_code")
	private String userVerifyCode;

}
