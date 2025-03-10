package io.saim.dash.coupon.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @NoArgsConstructor
public class DUMMY_GeneralUser extends DUMMY_ServiceUser {

	private final DUMMY_UserType userType = DUMMY_UserType.GENERAL;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<MemberVendor> vendors;

	public String getName() {
		return this.name;
	}

	public List<VendorGroup> getVendors() {
		return this.vendors.stream()
			.map(MemberVendor::getVendorGroup)
			.toList();
	}

	public static boolean isGeneralUser(DUMMY_ServiceUser user) {
		return user instanceof DUMMY_GeneralUser;
	}
}
