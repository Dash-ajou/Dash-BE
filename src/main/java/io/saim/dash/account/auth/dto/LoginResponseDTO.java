package io.saim.dash.account.auth.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;
	private String sessionId;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class User implements Serializable {
		private static final long serialVersionUID = 1L;

		private Long userId;
		private String userName;
		private String userEmail;
		private String userPhone;
		private String userType;
	}
}
