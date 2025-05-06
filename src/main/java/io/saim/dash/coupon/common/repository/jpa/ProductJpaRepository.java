package io.saim.dash.coupon.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
