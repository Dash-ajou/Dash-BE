package io.saim.dash.account.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

	@NotBlank(message = "전화번호는 필수 입력값입니다.")
	@Pattern(regexp = "^01[0-9]-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
	@JsonProperty("user_phone")  // ✅ JSON 요청의 "user_phone"을 generalPhone에 매핑
	private String generalPhone;

	@NotBlank(message = "비밀번호는 필수입니다.")
	@JsonProperty("user_password")  // ✅ JSON 요청의 "user_password"을 userPassword에 매핑
	private String userPassword;
}
