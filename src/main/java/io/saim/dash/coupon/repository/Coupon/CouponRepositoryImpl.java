package io.saim.dash.coupon.repository.Coupon;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.model.Coupon;

@Repository
public class CouponRepositoryImpl implements CouponRepository {

	@Override
	public Long save(Coupon coupon) {
		return 0L;
	}

	@Override
	public Coupon getById(Long id) {
		return null;
	}
}
