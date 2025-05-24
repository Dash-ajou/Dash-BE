package io.saim.dash.account.general.repository;

import java.util.Optional;

import io.saim.dash.account.general.model.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GeneralUserRepository extends JpaRepository<GeneralUser, Long> {

	Optional<GeneralUser> findById(Long id);
	Optional<GeneralUser> findByPhone(String phone);

	boolean existsByPhone(String phone);
	Optional<GeneralUser> findByEmail(String email);

	@Query("SELECT g FROM GeneralUser g WHERE LOWER(TRIM(g.email)) = LOWER(TRIM(:email))")
	Optional<GeneralUser> findByEmailIgnoreCase(@Param("email") String email);
}
