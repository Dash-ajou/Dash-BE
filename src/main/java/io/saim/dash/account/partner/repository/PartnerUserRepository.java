package io.saim.dash.account.partner.repository;

import io.saim.dash.account.partner.model.PartnerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PartnerUserRepository extends JpaRepository<PartnerUser, Long> {

	Optional<PartnerUser> findByPartnerName(String partnerName);
	Optional<PartnerUser> findByPhone(String phone);
	Optional<PartnerUser> findByEmail(String email);
}
