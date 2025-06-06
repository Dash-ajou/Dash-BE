package io.saim.dash.account.auth.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

	private Data data;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Data implements Serializable {
		private static final long serialVersionUID = 1L;
		private User user;
		private String sessionId;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class User implements Serializable {
		private static final long serialVersionUID = 1L;
		private Long userId;
		private String userName;
		private String userEmail;
		private String userPhone;
		private String userType;
	}

	public Long getUserId() {
		return this.data != null && this.data.user != null ? this.data.user.userId : null;
	}
}
