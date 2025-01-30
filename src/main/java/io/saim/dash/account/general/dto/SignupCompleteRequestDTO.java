package io.saim.dash.account.general.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupCompleteRequestDTO {

	@NotBlank(message = "사용자 유형은 필수입니다.")
	@JsonProperty("user_type")
	private String userType;

	@JsonProperty("general_id")
	private String generalId;

	@NotBlank(message = "사용자 이름은 필수입니다.")
	@JsonProperty("general_name")
	private String generalName;

	@NotBlank(message = "전화번호는 필수입니다.")
	@JsonProperty("general_phone")
	private String generalPhone;

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "유효한 이메일 주소를 입력하세요.")
	@JsonProperty("general_email")
	private String generalEmail;

	@JsonProperty("vendor_group_id")
	private Long vendorGroupId;

	@JsonProperty("department_id")
	private Long departmentId;
}
