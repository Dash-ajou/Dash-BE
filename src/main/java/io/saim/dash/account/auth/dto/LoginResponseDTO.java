package io.saim.dash.account.auth.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@JsonIgnore
	public GeneralUser toGeneralUser() {
		if (!"GENERAL".equalsIgnoreCase(user.getUserType())) {
			throw new IllegalStateException("해당 사용자는 GENERAL 타입이 아닙니다.");
		}
		return GeneralUser.builder()
			.id(user.getUserId())
			.ownerName(user.getUserName())
			.ownerEmail(user.getUserEmail())
			.ownerPhone(user.getUserPhone())
			.type(user.getUserType()) // 필요 시 UserType enum 변환
			.build();
	}

	@JsonIgnore
	public PartnerUser toPartnerUser() {
		if (!"PARTNER".equalsIgnoreCase(user.getUserType())) {
			throw new IllegalStateException("해당 사용자는 PARTNER 타입이 아닙니다.");
		}
		return PartnerUser.builder()
			.id(user.getUserId())
			.name(user.getUserName())
			.email(user.getUserEmail())
			.phone(user.getUserPhone())
			.build();
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
