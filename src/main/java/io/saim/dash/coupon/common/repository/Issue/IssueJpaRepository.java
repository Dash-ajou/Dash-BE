package io.saim.dash.coupon.common.repository.Issue;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.Issue;

public interface IssueJpaRepository extends JpaRepository<Issue, Long> {
}
