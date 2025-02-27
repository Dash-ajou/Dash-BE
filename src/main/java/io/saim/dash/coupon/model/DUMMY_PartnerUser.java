package io.saim.dash.coupon.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @NoArgsConstructor
public class DUMMY_PartnerUser extends DUMMY_ServiceUser {

	@Getter
	private String partnerName;

	@Getter
	private String partnerAddress;

	public String getOwnerName() {
		return this.name;
	}

}
