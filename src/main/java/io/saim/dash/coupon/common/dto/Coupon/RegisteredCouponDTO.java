package io.saim.dash.coupon.common.dto.Coupon;

import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.RegisterLog;
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

	public RegisteredCouponDTO(Issue issue, Coupon coupon, RegisterLog registerLog) {
		super(coupon, issue.getIssueActiveStatus());

		this.register = registerLog.getRegisteredUser();
		this.registered_at = registerLog.getRegisteredAt().toString();
	}
}
