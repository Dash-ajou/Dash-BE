package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity @NoArgsConstructor
public class DUMMY_GeneralUser extends DUMMY_ServiceUser {

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<UserVendor> vendors = new ArrayList<>();

	@Builder
	public DUMMY_GeneralUser(
		Long id, String name, String email, String phone, LocalDateTime joinedAt,
		List<UserVendor> vendors
	) {
		super(id, name, email, phone, joinedAt, DUMMY_UserType.GENERAL);
		this.vendors = vendors;
	}

	public String getName() {
		return this.name;
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

	public static boolean isGeneralUser(DUMMY_ServiceUser user) {
		return user instanceof DUMMY_GeneralUser;
	}
}
