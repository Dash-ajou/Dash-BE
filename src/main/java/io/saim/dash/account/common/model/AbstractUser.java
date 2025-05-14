package io.saim.dash.account.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractUser {
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String password;

	//사용자 유형 구분 메서드(일반 사용자와 파트너 사용자 구별)
	public abstract boolean isPartner();
}
