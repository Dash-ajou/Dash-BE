package io.saim.dash.coupon.common.repository.Coupon;

import io.saim.dash.coupon.common.model.CouponPaymentLog;

public interface CouponPaymentLogRepository {

	void save(CouponPaymentLog couponPayment);

	CouponPaymentLog findById(Long id);
}
