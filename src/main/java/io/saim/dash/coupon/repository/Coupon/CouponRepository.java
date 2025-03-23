package io.saim.dash.coupon.repository.Coupon;

import java.util.List;
import java.util.Optional;

import io.saim.dash.coupon.model.Coupon;

public interface CouponRepository {

	void save(Coupon coupon);

	Optional<Coupon> getById(Long id);

	void saveAll(List<Coupon> issuedCoupons);
}
