package io.saim.dash.account.general.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnifiedSignupRequestDTO {

	@JsonProperty("general_name")
	private String generalName;

	@NotBlank
	@JsonProperty("password")
	private String password;

	@NotBlank
	@JsonProperty("password_confirm")
	private String passwordConfirm;

	@JsonProperty("user_type")
	private String userType;

	@NotBlank
	@JsonProperty("general_phone")
	private String generalPhone;

	@JsonProperty("general_email")
	private String generalEmail;

	@JsonProperty("partner_name")
	private String partnerName;

	@JsonProperty("partner_address")
	private String partnerAddress;

	@JsonProperty("owner_name")
	private String ownerName;

	@JsonProperty("owner_phone")
	private String ownerPhone;

	@JsonProperty("owner_email")
	private String ownerEmail;
}
