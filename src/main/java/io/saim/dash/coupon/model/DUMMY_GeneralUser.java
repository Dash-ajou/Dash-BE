package io.saim.dash.coupon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity @NoArgsConstructor
public class DUMMY_GeneralUser extends DUMMY_ServiceUser {
	public String getName() {
		return this.name;
	}
}
