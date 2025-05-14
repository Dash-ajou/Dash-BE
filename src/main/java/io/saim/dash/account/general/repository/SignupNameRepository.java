package io.saim.dash.account.general.repository;

import java.util.Optional;

import io.saim.dash.account.general.model.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SignupNameRepository extends JpaRepository<GeneralUser, Long> {
	Optional<GeneralUser> findByGeneralId(Long generalId);
	Optional<GeneralUser> findByGeneralPhone(String generalPhone);
	boolean existsByGeneralPhone(String generalPhone);

	//수신자(받는 사람) 조회를 위한 메서드 추가
	Optional<GeneralUser> findByGeneralEmail(String generalEmail);

	@Query("SELECT g FROM GeneralUser g WHERE LOWER(TRIM(g.generalEmail)) = LOWER(TRIM(:email))")
	Optional<GeneralUser> findByGeneralEmailIgnoreCase(@Param("email") String email);
}
