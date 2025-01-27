package io.saim.dash.account.general.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "user")
public class SignupName {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long generalId;
	private String generalName;
	private String generalEmail;
	private String generalPhone;
	private LocalDateTime joinedAt;
	private Long vendorGroupId;
	private Long departmentId;
}
