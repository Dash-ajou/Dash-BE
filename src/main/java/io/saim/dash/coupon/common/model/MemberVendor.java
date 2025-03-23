package io.saim.dash.coupon.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class MemberVendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private VendorGroup vendorGroup;

	@ManyToOne
	private DUMMY_GeneralUser user;

	@Builder
	private MemberVendor(VendorGroup vendorGroup, DUMMY_GeneralUser user) {
		this.vendorGroup = vendorGroup;
		this.user = user;
	}
}
