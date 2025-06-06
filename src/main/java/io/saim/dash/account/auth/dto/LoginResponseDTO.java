package io.saim.dash.account.auth.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;

	@JsonProperty("session_id")
	private String sessionId;

	@JsonIgnore
	public Long getUserId() {
		return this.user != null ? this.user.getUserId() : null;
	}

	@Getter @Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class User implements Serializable {
		private static final long serialVersionUID = 1L;

		@JsonProperty("user_id")
		private Long userId;

		@JsonProperty("user_name")
		private String userName;

		@JsonProperty("user_email")
		private String userEmail;

		@JsonProperty("user_phone")
		private String userPhone;

		@JsonProperty("user_type")
		private String userType;
	}
}
