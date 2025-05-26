package io.saim.dash.coupon.common.dto.Coupon;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.CouponRegistration;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RegisteredCouponDTO {
	private Long id;
	private PartnerDTO partner;
	private CouponStatus status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime expired_at;

	private GeneralUser register; // 등록자

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime registered_at; // 등록일시

	public RegisteredCouponDTO(Issue issue, Coupon coupon) {
		this.id = coupon.getCouponId();
		this.partner = new PartnerDTO(issue.getRequest().getPartner());
		this.status = coupon.getCouponStatus();
		this.expired_at = coupon.getExpiredAt();

		this.register = null;
		this.registered_at = null;
	}

	public RegisteredCouponDTO(Issue issue, Coupon coupon, CouponRegistration couponRegistration) {
		this.id = coupon.getCouponId();
		this.partner = new PartnerDTO(issue.getRequest().getPartner());
		this.status = coupon.getCouponStatus();
		this.expired_at = coupon.getExpiredAt();

		if (couponRegistration == null) {
			this.register = null;
			this.registered_at = null;
		} else {
			this.register = couponRegistration.getRegisteredUser();
			this.registered_at = couponRegistration.getRegisteredAt();
		}
	}
}
