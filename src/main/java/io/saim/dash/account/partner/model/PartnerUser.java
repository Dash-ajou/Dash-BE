package io.saim.dash.account.partner.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "partner")
public class PartnerUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long partnerId;

	@Column(nullable = false)
	private String partnerName;

	@Column(nullable = false)
	private String partnerAddress;

	@Column(nullable = false)
	private String ownerName;

	@Column(nullable = false)
	private String ownerPhone;

	@Column(nullable = false, unique = true)
	private String ownerEmail;

	@Column(nullable = false)
	private boolean isTemporary;

	@Column(nullable = true)
	private LocalDateTime temporaryRegisterDate;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Getter
	@Column(nullable = false)
	private String password;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
