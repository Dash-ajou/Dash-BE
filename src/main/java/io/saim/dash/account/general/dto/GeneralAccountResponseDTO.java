package io.saim.dash.account.general.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeneralAccountResponseDTO {
	private String status;
	private String message;
	private Data data;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Data {
		private String generalName;
		private String generalEmail;
		private String generalPhone;
	}
}
