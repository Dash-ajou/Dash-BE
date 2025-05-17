/*
package io.saim.dash.coupon.common.dto.Coupon;

import io.saim.dash.coupon.common.model.Coupon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import io.saim.dash.coupon.common.constant.CouponStatus;

@RequiredArgsConstructor
@Getter
public class CouponBriefDTO {
	private final long coupon_id; // 쿠폰ID
	private final long issue_id; // 쿠폰 발행ID
	private final long product_id; // 상품ID
	private final String register_code; // 쿠폰등록코드
	private final CouponStatus status; // 쿠폰상태
	private final String expired_at; // 쿠폰 만료일자

	public CouponBriefDTO(Coupon coupon) {
		this.coupon_id = coupon.getId();
		this.issue_id = coupon.getIssue().getIssueId();
		this.product_id = coupon.getProduct().getProductId();
		this.register_code = coupon.getRegisterCode();
		this.status = coupon.getCouponStatus();
		this.expired_at = coupon.getExpiredAt().toString();
	}
}

 */
