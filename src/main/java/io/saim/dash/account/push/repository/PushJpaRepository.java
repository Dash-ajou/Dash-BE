package io.saim.dash.account.push.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.saim.dash.account.push.model.Push;

@Repository
public interface PushJpaRepository extends JpaRepository<Push, Long> {}
