package io.saim.dash.coupon.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @NoArgsConstructor
public class DUMMY_GeneralUser extends DUMMY_ServiceUser {

	public DUMMY_GeneralUser(
		Long id, String name, String email, String phone, LocalDateTime joinedAt,
		List<MemberVendor> vendors
	) {
		super(id, name, email, phone, joinedAt, DUMMY_UserType.GENERAL);
		this.vendors = vendors;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<MemberVendor> vendors = new ArrayList<>();

	public String getName() {
		return this.name;
	}

	public void addVendor(VendorGroup vendorGroup) {
		MemberVendor link = vendorGroup.linkMember(this);
		this.vendors.add(link);
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
