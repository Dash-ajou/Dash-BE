package io.saim.dash.account.general.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupNameRequestDTO {
	@JsonProperty("general_name")
	private String generalName;

}
