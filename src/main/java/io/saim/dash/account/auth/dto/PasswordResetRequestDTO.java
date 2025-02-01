package io.saim.dash.account.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequestDTO {

	@NotBlank(message = "전화번호는 필수입니다.")
	@JsonProperty("user_phone")
	private String userPhone;

	@NotBlank(message = "새 비밀번호는 필수입니다.")
	@Size(min = 8, max = 20, message = "비밀번호는 8~20자로 입력해야 합니다.")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
		message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.")
	@JsonProperty("new_password")
	private String newPassword;

	@NotBlank(message = "비밀번호 확인은 필수입니다.")
	@JsonProperty("new_password_confirm")
	private String newPasswordConfirm;
}
