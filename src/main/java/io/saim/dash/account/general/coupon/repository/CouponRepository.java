package io.saim.dash.account.general.coupon.repository;

import io.saim.dash.account.general.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	List<Coupon> findByProduct_Partner_PartnerId(Long partnerId);
}
