package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import io.saim.dash.coupon.common.model.QCouponPaymentCode;
import io.saim.dash.coupon.common.model.QCouponPaymentLog;
import io.saim.dash.coupon.common.model.QCouponRegistration;
import io.saim.dash.coupon.common.repository.jpa.CouponPaymentLogJpaRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponPaymentLogRepositoryImpl implements CouponPaymentLogRepository {

	private final CouponPaymentLogJpaRepository couponPaymentLogJpaRepository;
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public void save(CouponPaymentLog couponPayment) {
		couponPaymentLogJpaRepository.save(couponPayment);
	}

	@Override
	public CouponPaymentLog findById(Long paymentLogId) {
		return couponPaymentLogJpaRepository.findById(paymentLogId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.PAYMENT_LOG_NOT_FOUND));
	}

	@Override
	public List<CouponPaymentLog> findByFilter(PartnerUser partnerUser, BooleanBuilder filterBuilder, int page, int size) {
		QCouponPaymentLog couponPaymentLog = QCouponPaymentLog.couponPaymentLog;
		filterBuilder.and(couponPaymentLog.partner.eq(partnerUser));

		JPAQuery<CouponPaymentLog> paymentRequestJPAQuery = getPaymentLogJPAQuery(filterBuilder);
		addPaginateOptions(paymentRequestJPAQuery, page, size);
		return paymentRequestJPAQuery.fetch();
	}

	@Override
	public CouponPaymentLog findByCoupon(Coupon coupon) {
		QCouponPaymentLog qCouponPaymentLog = QCouponPaymentLog.couponPaymentLog;
		QCouponPaymentCode qCouponPaymentCode = QCouponPaymentCode.couponPaymentCode;

		return jpaQueryFactory.selectFrom(qCouponPaymentLog)
			.join(qCouponPaymentCode).on(qCouponPaymentLog.paymentCode.eq(qCouponPaymentCode))
			.where(qCouponPaymentCode.coupon.eq(coupon))
			.fetchOne();
	}

	private JPAQuery<CouponPaymentLog> getPaymentLogJPAQuery(BooleanBuilder filterBuilder) {
		QCouponPaymentLog couponPaymentLog = QCouponPaymentLog.couponPaymentLog;
		QCouponRegistration couponRegistration = QCouponRegistration.couponRegistration;

		return jpaQueryFactory.selectFrom(couponPaymentLog)
			.join(couponRegistration).on(couponRegistration.coupon.eq(couponPaymentLog.paymentCode.coupon))
			.where(filterBuilder);

	}

	private static void addPaginateOptions(JPAQuery<CouponPaymentLog> issueJPAQuery, int page, int size) {
		if (page <= 0)
			page = 1;
		if (size <= 10)
			size = 10;

		issueJPAQuery
			// Pagination
			.offset((page - 1) * size)
			.limit(size);
	}

}
