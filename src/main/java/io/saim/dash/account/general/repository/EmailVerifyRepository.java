package io.saim.dash.account.general.repository;

import io.saim.dash.account.general.model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerifyRepository extends JpaRepository<EmailVerification, Long> {
	Optional<EmailVerification> findByEmail(String email);
}
