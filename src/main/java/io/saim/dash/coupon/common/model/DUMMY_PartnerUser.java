package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;

@Entity
public class DUMMY_PartnerUser extends DUMMY_ServiceUser {

	private final DUMMY_UserType userType = DUMMY_UserType.PARTNER;

	@Getter
	private String partnerName; // businessName

	@Getter
	private String partnerAddress;

	public DUMMY_PartnerUser() {
		super(null, null, null, null, null, DUMMY_UserType.PARTNER);
	}

	@Builder
	public DUMMY_PartnerUser(
		Long id, String name, String email, String phone,
		LocalDateTime joinedAt,
		String partnerName, String partnerAddress
	) {
		super(id, name, email, phone, joinedAt, DUMMY_UserType.PARTNER);
		this.partnerName = partnerName;
		this.partnerAddress = partnerAddress;
	}

	public String getOwnerName() {
		return this.name;
	}

	public static boolean isPartnerUser(DUMMY_ServiceUser user) {
		return user instanceof DUMMY_PartnerUser;
	}

}
