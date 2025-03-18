package io.saim.dash.coupon.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VendorGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "vendorGroup", fetch = FetchType.LAZY)
	private List<MemberVendor> members;

	@OneToMany(mappedBy = "vendorGroup", fetch = FetchType.LAZY)
	private List<Issue> issues;

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
}
