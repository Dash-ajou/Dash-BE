package io.saim.dash.account.partner.model;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.general.model.GeneralUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@AttributeOverride(name = "email", column = @Column(name = "owner_email", nullable = true, unique = true)),
	@AttributeOverride(name = "name", column = @Column(name = "owner_name", nullable = true)),
	@AttributeOverride(name = "phone", column = @Column(name = "owner_phone", nullable = false)),
	@AttributeOverride(name = "joinedAt", column = @Column(name = "joined_at", nullable = true))
})

public class PartnerUser extends ServiceUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partner_id")
	private Long id;

	@Column(name = "owner_name", nullable = true)
	private String name;

	@Column(name = "owner_email", nullable = true, unique = true)
	private String email;

	@Column(name = "owner_phone", nullable = false)
	private String phone;

	@Column(name = "joined_at", nullable = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime joinedAt;

	@Column(name = "partner_name", nullable = false)
	private String partnerName;

	@Column(name = "partner_address", nullable = true)
	private String partnerAddress;

	@Column(nullable = false)
	private boolean isTemporary;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime temporaryRegisterDate;

	@Column(nullable = true)
	private String password;

	@PrePersist
	protected void onCreate() {
		if (this.joinedAt == null) {
			this.joinedAt = LocalDateTime.now();
		}
	}

	public String getOwnerName() {
		return getName();
	}

	public static boolean isPartnerUser(ServiceUser serviceUser) {
		return serviceUser instanceof PartnerUser;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof PartnerUser)) return false;
		PartnerUser that = (PartnerUser) o;
		return Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public Boolean isPartner() {
		return true;
	}
}
