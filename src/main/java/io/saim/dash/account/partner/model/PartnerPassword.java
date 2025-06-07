package io.saim.dash.account.partner.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "partner_passwords")
public class PartnerPassword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "partner_id", nullable = false)
	private PartnerUser user;

	@Column(name = "hashed_password", nullable = false)
	private String hashedPassword;

	@CreationTimestamp
	private LocalDateTime createdAt;

	public void changePassword(String newPassword, BCryptPasswordEncoder encoder) {
		this.hashedPassword = encoder.encode(newPassword);
	}
}
