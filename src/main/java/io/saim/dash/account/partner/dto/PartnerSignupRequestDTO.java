package io.saim.dash.account.partner.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerSignupRequestDTO {

	@NotBlank
	@JsonProperty("password")
	private String password;

	@NotBlank
	@JsonProperty("password_confirm")
	private String passwordConfirm;

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

	@JsonProperty("is_temporary")
	private boolean isTemporary;

	@JsonProperty("temporary_register_date")
	private LocalDateTime temporaryRegisterDate;
}
