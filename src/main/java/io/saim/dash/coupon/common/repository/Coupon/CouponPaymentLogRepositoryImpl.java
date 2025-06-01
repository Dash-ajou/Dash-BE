package io.saim.dash.coupon.common.repository.Coupon;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.common.model.CouponPaymentLog;
import io.saim.dash.coupon.common.repository.jpa.CouponPaymentLogJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponPaymentLogRepositoryImpl implements CouponPaymentLogRepository {

	private final CouponPaymentLogJpaRepository couponPaymentLogJpaRepository;

	@Override
	public void save(CouponPaymentLog couponPayment) {
		couponPaymentLogJpaRepository.save(couponPayment);
	}


}
