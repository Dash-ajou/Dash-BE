package io.saim.dash.account.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
	private String status;
	private String message;
	private Data data;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Data {
		private User user;
		private String sessionId;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class User {
		private String userName;
		private String userEmail;
		private String userPhone;
		private String userType;
	}
}
