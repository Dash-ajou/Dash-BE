package io.saim.dash.coupon.repository.DUMMY;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DUMMY_PartnerUserRepositoryImpl implements DUMMY_PartnerUserRepository {

	private final DUMMY_PartnerUserJPARepository partnerUserJPARepository;

	@Override
	public DUMMY_PartnerUser findPartnerByBusinessName(String businessName) {
		return null;
	}

	@Override
	public void save(DUMMY_PartnerUser partner) {

	}
}

interface DUMMY_PartnerUserJPARepository extends JpaRepository<DUMMY_PartnerUser, Long> {}
