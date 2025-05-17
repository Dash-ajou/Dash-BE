package io.saim.dash.account.partner.model;

import io.saim.dash.account.common.model.ServiceUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "partner_user", uniqueConstraints = {
	@UniqueConstraint(name = "UK_partner_phone", columnNames = "owner_phone")
})

@AttributeOverrides({
	@AttributeOverride(name = "email", column = @Column(name = "owner_email", nullable = false, unique = true)),
	@AttributeOverride(name = "name", column = @Column(name = "owner_name", nullable = false)),
	@AttributeOverride(name = "phone", column = @Column(name = "owner_phone", nullable = false)),
	@AttributeOverride(name = "joinedAt", column = @Column(name = "joined_at", nullable = false))
})

public class PartnerUser extends ServiceUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partner_id")
	private Long id;

	@Column(name = "owner_name", nullable = false)
	private String name;

	@Column(name = "owner_email", nullable = false, unique = true)
	private String email;

	@Column(name = "owner_phone", nullable = false)
	private String phone;

	@Column(name = "joined_at", nullable = false)
	private LocalDateTime joinedAt;

	@Column(name = "partner_name", nullable = false)
	private String partnerName;

	@Column(name = "partner_address", nullable = false)
	private String partnerAddress;

	@Column(nullable = false)
	private boolean isTemporary;

	@Column
	private LocalDateTime temporaryRegisterDate;

	@Column(nullable = false)
	private String password;

	@PrePersist
	protected void onCreate() {
		if (this.joinedAt == null) {
			this.joinedAt = LocalDateTime.now();
		}
	}

	public String getOwnerName() {
		return getName(); // from ServiceUser
	}
}
