package io.saim.dash.account.general.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupCompleteResponseDTO {
	private String status;
	private String message;
	private Data data;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Data {
		private String generalId;
		private String generalType;
		private String generalName;
		private String generalPhone;
		private String generalEmail;
		private String joinedAt;
		private Long vendorGroupId;
		private Long departmentId;
	}
}
