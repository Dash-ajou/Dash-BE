package io.saim.dash.account.partner.model;

import io.saim.dash.account.common.model.ServiceUser;
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
public class PartnerUser extends ServiceUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partner_id")
	private Long id;

	// 명시적 getter
	@Getter
	@Column(name = "partner_name", nullable = false)
	private String partnerName;  // = business name

	@Getter
	@Column(name = "partner_address", nullable = false)
	private String partnerAddress;

	@Column(name = "owner_name", nullable = false)
	private String name;  // from ServiceUser

	@Column(name = "owner_phone", nullable = false)
	private String phone;  // from ServiceUser

	@Column(name = "owner_email", nullable = false, unique = true)
	private String email;  // from ServiceUser

	@Column(nullable = false)
	private boolean isTemporary;

	@Column
	private LocalDateTime temporaryRegisterDate;

	@Column(nullable = false)
	private LocalDateTime joinedAt;

	@Column(nullable = false)
	private String password;

	@PrePersist
	protected void onCreate() {
		this.joinedAt = LocalDateTime.now();
	}

	public String getOwnerName() {
		return name;
	}
}
