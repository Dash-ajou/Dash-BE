package io.saim.dash.account.partner.repository;

import io.saim.dash.account.partner.model.PartnerPassword;
import io.saim.dash.account.partner.model.PartnerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerPasswordRepository extends JpaRepository<PartnerPassword, Long> {
	List<PartnerPassword> findByUser(PartnerUser user);
}
