package io.saim.dash.account.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
	private Long userId;
	private String userName;
	private String userEmail;
	private String userPhone;
	private String userType;
	private String sessionId;
}
