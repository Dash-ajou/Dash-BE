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
public class LoginResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String userName;
	private String userEmail;
	private String userPhone;
	private String userType;
	private String sessionId;
}
