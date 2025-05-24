package io.saim.dash.account.general.model;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.model.UserVendor;
import io.saim.dash.coupon.common.model.Vendor;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id")
	private PartnerUser partner;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<UserVendor> vendors = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Password> passwords;

	public static boolean isGeneralUser(ServiceUser serviceUser) {
		return serviceUser instanceof GeneralUser;
	}

	public List<Password> getPasswordHistory() {
		return passwords;
	}

	public boolean isPasswordValid(String rawPassword, Password password, BCryptPasswordEncoder encoder) {
		return encoder.matches(rawPassword.trim(), password.getHashedPassword());
	}

	public void addVendor(Vendor vendor) {
		UserVendor link = vendor.linkMember(this);
		this.vendors.add(link);
	}

	public List<Vendor> getVendors() {
		return this.vendors.stream()
			.map(UserVendor::getVendor)
			.toList();
	}
}
