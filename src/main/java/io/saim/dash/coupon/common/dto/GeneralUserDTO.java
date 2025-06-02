package io.saim.dash.coupon.common.dto;

import io.saim.dash.account.general.model.GeneralUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class GeneralUserDTO {
	private String name;
	private String email;
	private String phone;

	public GeneralUserDTO(GeneralUser user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.phone = user.getPhone();
	}
}
