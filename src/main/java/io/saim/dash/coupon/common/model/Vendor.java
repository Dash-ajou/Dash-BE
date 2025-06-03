package io.saim.dash.coupon.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.general.model.GeneralUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Vendor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendor_id")
	@Getter private Long vendorId;

	@Getter private String name;
	@Getter private String presidentName;
	@Getter private String presidentPhone;

	@OneToMany(
		mappedBy = "vendor", fetch = FetchType.LAZY,
		cascade = CascadeType.ALL, orphanRemoval = true
	)
	private List<UserVendor> vendorUsers = new ArrayList<>();

	@OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
	private List<Request> requests;

	private LocalDateTime createdAt = LocalDateTime.now();

	@Builder
	private Vendor(
		String name,
		String presidentName, String presidentPhone
	) {
		this.name = name;
		this.presidentName = presidentName;
		this.presidentPhone = presidentPhone;
	}

	public boolean isMemberIncluded(ServiceUser user) {
		if (user.isPartner())
			return false;
		if (!(user instanceof GeneralUser generalUser))
			return false;

		return this.vendorUsers.stream()
			.map(UserVendor::getUser)
			.anyMatch(vu -> vu.getId().equals(generalUser.getId()));
	}

	private List<GeneralUser> getVendorUsers() {
		return this.vendorUsers.stream()
			.map(UserVendor::getUser)
			.toList();
	}

	public UserVendor linkMember(GeneralUser user) {
		UserVendor link = UserVendor.builder()
			.vendor(this)
			.user(user)
			.build();

		this.vendorUsers.add(link);

		return link;
	}
}
