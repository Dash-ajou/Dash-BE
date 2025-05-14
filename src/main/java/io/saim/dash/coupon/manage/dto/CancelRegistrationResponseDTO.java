package io.saim.dash.coupon.manage.dto;

import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;

public class CancelRegistrationResponseDTO {
	private final Coupon coupon;
	private final DUMMY_GeneralUser register;

	public CancelRegistrationResponseDTO(CouponRegistration registration) {
		this.coupon = registration.getCoupon();
		this.register = registration.getRegisteredUser();
	}
}
