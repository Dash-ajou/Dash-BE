package io.saim.dash.account.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GoogleTokenVerifyDataDTO {
	private String userId;
	private String userEmail;
	private boolean emailVerified;
}
