package io.saim.dash.account.general.repository;

import java.util.Optional;

import io.saim.dash.account.general.model.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignupNameRepository extends JpaRepository<GeneralUser, Long> {
	Optional<GeneralUser> findByGeneralId(Long generalId);
	Optional<GeneralUser> findByGeneralPhone(String generalPhone);
	boolean existsByGeneralPhone(String generalPhone);
}
