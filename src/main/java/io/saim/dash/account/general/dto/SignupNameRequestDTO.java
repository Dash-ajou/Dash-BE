package io.saim.dash.account.general.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupNameRequestDTO {
	@JsonProperty("general_name")
	@NotBlank(message = "이름은 필수 입력값입니다.")
	private String generalName;
}
