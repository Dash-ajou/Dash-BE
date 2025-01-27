package io.saim.dash.account.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import io.saim.dash.account.auth.model.PhoneVerification;

public interface PhoneVerificationRepository extends JpaRepository<PhoneVerification, Long> {
	Optional<PhoneVerification> findByUserPhone(String userPhone);
}
