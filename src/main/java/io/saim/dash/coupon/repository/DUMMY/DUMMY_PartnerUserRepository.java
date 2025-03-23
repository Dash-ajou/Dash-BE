package io.saim.dash.coupon.repository.DUMMY;

import io.saim.dash.coupon.model.DUMMY_PartnerUser;

public interface DUMMY_PartnerUserRepository {
	DUMMY_PartnerUser findPartnerByBusinessName(String businessName);

	void save(DUMMY_PartnerUser partner);
}
