package io.saim.dash.account.general.coupon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRegisterRequestDTO {
	@JsonProperty("coupon_number")
	private String couponNumber;
}
