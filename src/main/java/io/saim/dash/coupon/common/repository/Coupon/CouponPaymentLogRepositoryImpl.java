package io.saim.dash.coupon.common.repository.Coupon;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.common.model.CouponPaymentLog;
import io.saim.dash.coupon.common.repository.jpa.CouponPaymentLogJpaRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponPaymentLogRepositoryImpl implements CouponPaymentLogRepository {

	private final CouponPaymentLogJpaRepository couponPaymentLogJpaRepository;

	@Override
	public void save(CouponPaymentLog couponPayment) {
		couponPaymentLogJpaRepository.save(couponPayment);
	}

	@Override
	public CouponPaymentLog findById(Long paymentLogId) {
		return couponPaymentLogJpaRepository.findById(paymentLogId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.PAYMENT_LOG_NOT_FOUND));
	}

}
