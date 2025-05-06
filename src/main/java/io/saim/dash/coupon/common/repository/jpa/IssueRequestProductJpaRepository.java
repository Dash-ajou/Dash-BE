package io.saim.dash.coupon.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.mapping.RequestProduct;

public interface IssueRequestProductJpaRepository extends JpaRepository<RequestProduct, Long> {}
