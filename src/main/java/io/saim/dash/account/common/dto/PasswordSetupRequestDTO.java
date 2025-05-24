package io.saim.dash.account.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordSetupRequestDTO {
	private Long generalId;
	private Long partnerId;
	private String password;
	private String passwordConfirm;

	public boolean isGeneralUser() {
		return generalId != null;
	}

	public boolean isPartnerUser() {
		return partnerId != null;
	}
}
