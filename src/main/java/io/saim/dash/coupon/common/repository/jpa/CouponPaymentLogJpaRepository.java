package io.saim.dash.coupon.common.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.common.constant.PaymentStatus;
import io.saim.dash.coupon.common.model.CouponPaymentLog;

@Repository
public interface CouponPaymentLogJpaRepository extends JpaRepository<CouponPaymentLog, Long> {

	CouponPaymentLog save(CouponPaymentLog couponPaymentLog);

	Optional<CouponPaymentLog> findByPaidPaymentCode(String paidPaymentCode);

	Optional<CouponPaymentLog> findByPaidPaymentCodeAndStatus(String paidPaymentCode, PaymentStatus status);

}
