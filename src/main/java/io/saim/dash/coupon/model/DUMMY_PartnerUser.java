package io.saim.dash.coupon.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @NoArgsConstructor
public class DUMMY_PartnerUser extends DUMMY_ServiceUser {

	private final DUMMY_UserType userType = DUMMY_UserType.PARTNER;

	@Getter
	private String partnerName;

	@Getter
	private String partnerAddress;

	public String getOwnerName() {
		return this.name;
	}

	public static boolean isPartnerUser(DUMMY_ServiceUser user) {
		return user instanceof DUMMY_PartnerUser;
	}

}
