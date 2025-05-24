package io.saim.dash.account.general.repository;

import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.model.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GeneralPasswordRepository extends JpaRepository<Password, Long> {
	List<Password> findByUser(GeneralUser user);
}
