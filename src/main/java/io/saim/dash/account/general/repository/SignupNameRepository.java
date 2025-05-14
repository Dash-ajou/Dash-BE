package io.saim.dash.account.general.repository;

import io.saim.dash.account.general.model.SignupName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignupNameRepository extends JpaRepository<SignupName, Long> {
}
