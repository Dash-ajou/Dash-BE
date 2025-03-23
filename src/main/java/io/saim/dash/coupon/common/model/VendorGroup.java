package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@Getter @Setter
public class VendorGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String presidentName;
	private String presidentPhone;

	@OneToMany(mappedBy = "vendorGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MemberVendor> members = new ArrayList<>();

	@OneToMany(mappedBy = "vendorGroup", fetch = FetchType.LAZY)
	private List<Issue> issues;

	private LocalDateTime createdAt = LocalDateTime.now();

	@Builder
	private VendorGroup(
		String name, String presidentName, String presidentPhone
	) {
		this.name = name;
		this.presidentName = presidentName;
		this.presidentPhone = presidentPhone;
	}

	public boolean isMemberIncluded(DUMMY_ServiceUser user) {
		if (!DUMMY_GeneralUser.isGeneralUser(user))
			return false;

		return this.getMembers().contains(user);
	}

	private List<DUMMY_GeneralUser> getMembers() {
		return this.members.stream().map(
			MemberVendor::getUser
		).toList();
	}

	public MemberVendor linkMember(DUMMY_GeneralUser user) {
		MemberVendor link = MemberVendor.builder()
			.vendorGroup(this)
			.user(user)
			.build();

		this.members.add(link);

		return link;
	}
}
