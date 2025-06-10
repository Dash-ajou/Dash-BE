package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.constant.PaymentStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentLog;

public interface CouponPaymentLogRepository {

	void save(CouponPaymentLog couponPayment);

	CouponPaymentLog findById(Long id);

	List<CouponPaymentLog> findByFilter(PartnerUser partnerUser, BooleanBuilder filterBuilder, int page, int size);

	CouponPaymentLog findByCoupon(Coupon coupon);

	CouponPaymentLog findByPaymentCode(String paymentCode, PaymentStatus paymentStatus);

	CouponPaymentLog findByPaymentCodeNormal(String paymentCode, PaymentStatus paymentStatus);
}
