package io.saim.dash.account.general.model;

import io.saim.dash.account.common.model.ServiceUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "general_user", uniqueConstraints = {
	@UniqueConstraint(name = "UK_general_phone", columnNames = "owner_phone")
})
public class GeneralUser extends ServiceUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "general_id")
	private Long id;

	@Column(name = "owner_name", nullable = false)
	private String ownerName;

	@Column(name = "owner_email", nullable = false, unique = true)
	private String ownerEmail;

	@Column(name = "owner_phone", nullable = false, unique = true)
	private String ownerPhone;

	@Column(name = "joined_at", nullable = false)
	private LocalDateTime joinedAt;

	@Column(name = "general_type")
	private String type;

	@Column(nullable = false)
	private String password = "DUMMY_PASSWORD";

	private Long vendorGroupId;
	private Long departmentId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Password> passwords;

	public List<Password> getPasswordHistory() {
		return passwords;
	}

	public boolean isPasswordValid(String rawPassword, Password password, BCryptPasswordEncoder encoder) {
		return encoder.matches(rawPassword.trim(), password.getHashedPassword());
	}
}
