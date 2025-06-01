package io.saim.dash.coupon.common.util;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.model.QCouponPaymentLog;
import io.saim.dash.coupon.common.model.QCouponRegistration;

public class PaymentQueryHelper {

	public static BooleanBuilder createPaymentLogSearchFilterBuilder(
		Long paymentId, String paymentCode,
		Long couponId,
		String userName
	) {
		BooleanBuilder builder = new BooleanBuilder();

		addPaymentIdFilterToPaymentLog(builder, paymentId);
		addPaymentCodeFilterToPaymentLog(builder, paymentCode);
		addCouponIdFilterToPaymentLog(builder, couponId);
		addUserNameFilterToPaymentLog(builder, userName);

		return builder;
	}

	private static void addUserNameFilterToPaymentLog(BooleanBuilder builder, String userName) {
		if (userName == null) return;

		QCouponRegistration couponRegistration = QCouponRegistration.couponRegistration;
		builder.and(couponRegistration.registeredUser.name.eq(userName));
	}

	private static void addCouponIdFilterToPaymentLog(BooleanBuilder builder, Long couponId) {
		if (couponId == null) return;

		QCouponPaymentLog couponPaymentLog = QCouponPaymentLog.couponPaymentLog;
		builder.and(couponPaymentLog.paymentCode.coupon.couponId.eq(couponId));
	}

	private static void addPaymentCodeFilterToPaymentLog(BooleanBuilder builder, String paymentCode) {
		if (paymentCode == null) return;

		QCouponPaymentLog couponPaymentLog = QCouponPaymentLog.couponPaymentLog;
		builder.and(couponPaymentLog.paymentCode.paymentCode.eq(paymentCode));
	}

	private static void addPaymentIdFilterToPaymentLog(BooleanBuilder builder, Long paymentId) {
		if (paymentId == null) return;

		QCouponPaymentLog couponPaymentLog = QCouponPaymentLog.couponPaymentLog;
		builder.and(couponPaymentLog.paymentId.eq(paymentId));
	}
}
