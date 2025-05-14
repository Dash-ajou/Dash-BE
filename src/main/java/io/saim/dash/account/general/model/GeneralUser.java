package io.saim.dash.account.general.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {
	@UniqueConstraint(name = "UK_general_phone", columnNames = "general_phone")
})
public class GeneralUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long generalId; //PK
	private String generalType;
	private String generalName;
	private String generalEmail;

	@Column(unique = true, nullable = false)
	private String generalPhone;

	@Getter
	@Column(nullable = false)
	private String password = "DUMMY_PASSWORD";

	private LocalDateTime joinedAt;
	private Long vendorGroupId;
	private Long departmentId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Password> passwords;

	//비밀번호 검증 메서드 (입력된 비밀번호와 DB 비밀번호 비교)
	public boolean isPasswordValid(String rawPassword, Password password, BCryptPasswordEncoder encoder) {
		return encoder.matches(rawPassword.trim(), password.getHashedPassword());
	}
}
