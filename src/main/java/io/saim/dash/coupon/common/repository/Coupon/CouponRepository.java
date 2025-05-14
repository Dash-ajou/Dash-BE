package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.model.Coupon;

public interface CouponRepository {
	List<Coupon> findCouponsByIssueId(Long issueId);

	Coupon findCouponById(Long couponId);

	Long cancelCoupons(BooleanBuilder couponFilterBuilder);
}
