package io.saim.dash.account.auth.dto;

import io.saim.dash.common.constants.ValidationMessages;
import io.saim.dash.common.constants.ValidationPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

	@NotBlank(message = ValidationMessages.PHONE_REQUIRED)
	@Pattern(regexp = ValidationPatterns.PHONE_REGEX, message = ValidationMessages.PHONE_INVALID_FORMAT)
	@JsonProperty("user_phone")
	private String userPhone;

	@NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
	@JsonProperty("user_password")
	private String userPassword;

	@NotBlank(message = "사용자 타입은 필수입니다.")
	@JsonProperty("user_type")
	private String userType;
}
