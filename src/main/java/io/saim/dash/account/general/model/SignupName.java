package io.saim.dash.account.general.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "user")
public class SignupName {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long generalId; //PK
	private String generalName;
	private String generalEmail;
	private String generalPhone;
	private LocalDateTime joinedAt;
	private Long vendorGroupId;
	private Long departmentId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Password> passwords;
}
