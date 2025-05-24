package io.saim.dash.coupon.manage.dto;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;

public class CancelRegistrationResponseDTO {
	private final Coupon coupon;
	private final GeneralUser register;

	public CancelRegistrationResponseDTO(CouponRegistration registration) {
		this.coupon = registration.getCoupon();
		this.register = registration.getRegisteredUser();
	}
}
