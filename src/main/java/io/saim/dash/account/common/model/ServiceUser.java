package io.saim.dash.account.common.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class ServiceUser {
	private Long id;
	private String name;
	private String email;
	private String phone;
	private LocalDateTime joinedAt;

	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public LocalDateTime getJoinedAt() {
		return joinedAt;
	}
	public Long getId() {
		return id;
	}
}
