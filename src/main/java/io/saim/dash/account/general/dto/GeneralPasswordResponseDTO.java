package io.saim.dash.account.general.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeneralPasswordResponseDTO {
	private String status;
	private String message;
	private Data data;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Data {
		private Long generalId;
	}
}
