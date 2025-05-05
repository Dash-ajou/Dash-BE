package io.saim.dash.coupon.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.IssueRequest;

public interface IssueRequestJpaRepository extends JpaRepository<IssueRequest, Long> {
}
