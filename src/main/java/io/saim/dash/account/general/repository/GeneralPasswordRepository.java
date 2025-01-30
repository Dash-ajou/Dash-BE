package io.saim.dash.account.general.repository;

import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.model.SignupName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GeneralPasswordRepository extends JpaRepository<Password, Long> {
	Optional<Password> findByUser(SignupName user);
}
