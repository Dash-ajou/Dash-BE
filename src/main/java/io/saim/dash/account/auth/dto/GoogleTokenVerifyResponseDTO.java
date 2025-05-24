package io.saim.dash.account.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GoogleTokenVerifyResponseDTO {
	private String status;
	private String message;
	private Data data;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Data {
		private String userId;
		private String userEmail;
		private boolean emailVerified;
	}
}
