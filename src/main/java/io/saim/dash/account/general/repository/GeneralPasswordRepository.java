package io.saim.dash.account.general.repository;

import io.saim.dash.account.general.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralPasswordRepository extends JpaRepository<Password, Long> {
}
