package io.saim.dash.coupon.common.repository.Coupon;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.Coupon;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
	Optional<Coupon> findByCode(String code);
}
