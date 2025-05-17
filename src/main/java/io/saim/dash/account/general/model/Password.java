package io.saim.dash.account.general.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "passwords")
public class Password {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "general_id", nullable = false)
	private GeneralUser user;

	@Column(name = "hashed_password", nullable = false)
	private String hashedPassword;

	@CreationTimestamp
	private LocalDateTime createdAt;

	public static Password getLatestPassword(List<Password> passwords) {
		return passwords.stream()
			.max((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()))
			.orElse(null);
	}

	public void changePassword(String newPassword, BCryptPasswordEncoder encoder) {
		this.hashedPassword = encoder.encode(newPassword);
	}
}
