package io.saim.dash.coupon.common.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Request;

public interface IssueJpaRepository extends JpaRepository<Issue, Long> {
	Issue findByRequest(Request request);
}
