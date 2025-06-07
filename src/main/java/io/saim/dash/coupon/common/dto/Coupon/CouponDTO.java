package io.saim.dash.coupon.common.dto.Coupon;

import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponDTO {
	private final long coupon_id; // 쿠폰ID
	private final long issue_id; // 쿠폰 발행ID
	@Getter private final CouponStatus status; // 쿠폰상태
	private final String expired_at; // 쿠폰 만료일자
	@Getter private final Product product; // 상품
	@Getter private final PartnerUser partner; // 파트너
	private final String register_code; // 쿠폰등록코드

	public CouponDTO (Coupon coupon, IssueActiveStatus issueActiveStatus) {
		this.coupon_id = coupon.getCouponId();
		this.issue_id = coupon.getIssue().getIssueId();
		this.expired_at = coupon.getExpiredAt().toString();
		this.product = coupon.getProduct();
		this.partner = this.product.getPartner();
		this.register_code = coupon.getRegistrationCode();

		if (issueActiveStatus == IssueActiveStatus.DISABLE)
			this.status = CouponStatus.DISABLED;
		else this.status = coupon.getCouponStatus();
	}

	public long getCouponId() {
		return coupon_id;
	}
	public long getIssueId() {
		return issue_id;
	}
	public String getExpiredAt() {
		return expired_at;
	}
	public String getRegisterCode() {
		return register_code;
	}
}
