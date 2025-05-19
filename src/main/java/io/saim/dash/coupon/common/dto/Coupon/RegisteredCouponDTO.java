/*
package io.saim.dash.coupon.common.dto.Coupon;

import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.CouponRegistration;
import lombok.Getter;

@Getter
public class RegisteredCouponDTO extends CouponDTO {
	private final DUMMY_GeneralUser register; // 등록자
	private final String registered_at; // 등록일시

	public RegisteredCouponDTO(Issue issue, Coupon coupon) {
		super(coupon, issue.getIssueActiveStatus());

		this.register = null;
		this.registered_at = null;
	}

	public RegisteredCouponDTO(Issue issue, Coupon coupon, CouponRegistration couponRegistration) {
		super(coupon, issue.getIssueActiveStatus());

		if (couponRegistration == null) {
			this.register = null;
			this.registered_at = null;
		} else {
			this.register = couponRegistration.getRegisteredUser();
			this.registered_at = couponRegistration.getRegisteredAt().toString();
		}
	}
}

 */
