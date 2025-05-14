package io.saim.dash.account.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordResetResponseDTO {
	private String status;
	private String message;
}
