package io.saim.dash.coupon.common.dto.Coupon;

import io.saim.dash.coupon.common.constant.CodeType;
import io.saim.dash.coupon.common.model.Coupon;

public record CouponCodeInfo(
	CodeType codeType,
	Coupon coupon
){

}
