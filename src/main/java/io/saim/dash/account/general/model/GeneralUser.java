package io.saim.dash.account.general.model;

import io.saim.dash.account.common.model.ServiceUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {
	@UniqueConstraint(name = "UK_general_phone", columnNames = "general_phone")
})
public class GeneralUser extends ServiceUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "general_id")
	private Long id;

	@Column(name = "general_type")
	private String type;

	@Column(name = "general_phone", unique = true, nullable = false)
	private String phone;

	@Column(nullable = false)
	private String password = "DUMMY_PASSWORD";

	private Long vendorGroupId;
	private Long departmentId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Password> passwords;

	public List<Password> getPasswordHistory() {
		return passwords;
	}

	//비밀번호 검증 메서드
	public boolean isPasswordValid(String rawPassword, Password password, BCryptPasswordEncoder encoder) {
		return encoder.matches(rawPassword.trim(), password.getHashedPassword());
	}
}
