package io.saim.dash.account.partner.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerSignupRequestDTO {

	@NotBlank(message = "비밀번호는 필수입니다.")
	@JsonProperty("password")
	private String password;

	@NotBlank(message = "비밀번호 확인은 필수입니다.")
	@JsonProperty("password_confirm")
	private String passwordConfirm;

	@JsonProperty("partner_name")
	private String partnerName;

	@JsonProperty("partner_address")
	private String partnerAddress;

	@JsonProperty("owner_name")
	private String ownerName;

	@NotBlank(message = "전화번호는 필수입니다.")
	@JsonProperty("owner_phone")
	private String ownerPhone;

	@JsonProperty("owner_email")
	private String ownerEmail;

	@JsonProperty("is_temporary")
	private boolean isTemporary;

	@JsonProperty("temporary_register_date")
	private LocalDateTime temporaryRegisterDate;

	@JsonProperty("user_type")
	private final String userType = "PARTNER";
}
