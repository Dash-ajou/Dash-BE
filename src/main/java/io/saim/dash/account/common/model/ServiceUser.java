package io.saim.dash.account.common.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class ServiceUser implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String name;
	protected String email;
	protected String phone;
	protected LocalDateTime joinedAt;

	@Transient
	private UserType userType;

	public abstract Long getId();

	public Boolean isPartner() {
		return this.userType == UserType.PARTNER;
	}
}
