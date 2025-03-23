package io.saim.dash.coupon.repository.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.model.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
