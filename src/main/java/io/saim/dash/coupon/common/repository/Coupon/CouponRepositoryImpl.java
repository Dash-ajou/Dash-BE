package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.QCoupon;
import io.saim.dash.coupon.common.repository.jpa.CouponJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

	private final JPAQueryFactory queryFactory;
	private final CouponJpaRepository couponJpaRepository;

	@Override
	public List<Coupon> findCouponsByIssueId(Long issueId) {
		return couponJpaRepository.findByIssueIssueId(issueId);
	}

	@Override
	public Coupon findCouponById(Long couponId) {
		return couponJpaRepository.findById(couponId).orElse(null);
	}

	@Override
	public Long cancelCoupons(BooleanBuilder couponFilterBuilder) {
		QCoupon coupon = QCoupon.coupon;
		return queryFactory
			.update(coupon)
			.set(coupon.couponStatus, CouponStatus.CANCELED)
			.where(couponFilterBuilder)
			.execute();
	}

}
