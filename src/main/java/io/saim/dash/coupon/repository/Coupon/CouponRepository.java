package io.saim.dash.coupon.repository.Coupon;

import io.saim.dash.coupon.model.Coupon;

public interface CouponRepository {

	Long save(Coupon coupon);

	Coupon getById(Long id);

}
