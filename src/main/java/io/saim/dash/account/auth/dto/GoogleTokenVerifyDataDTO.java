package io.saim.dash.account.auth.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GoogleTokenVerifyDataDTO implements Serializable {
	private String userId;
	private String userEmail;
	private boolean emailVerified;
}
