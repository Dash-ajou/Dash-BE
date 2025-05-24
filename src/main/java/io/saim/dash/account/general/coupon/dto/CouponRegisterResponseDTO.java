package io.saim.dash.account.general.coupon.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponRegisterResponseDTO {
	private Long couponId;
	private String couponName;
	private String partnerName;
	private String validUntil;
}
