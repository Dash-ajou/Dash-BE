package io.saim.dash.coupon.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public abstract class DUMMY_ServiceUser { // SignupName 수정 시 해당 class로 대체

	@Id @Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	protected String name;

	@Getter
	protected String email;

	@Getter
	protected String phone;

	@Getter
	protected LocalDateTime joinedAt;

	@Transient
	private DUMMY_UserType userType;

	public boolean isPartner() {
		return this.userType == DUMMY_UserType.PARTNER;
	}
}
