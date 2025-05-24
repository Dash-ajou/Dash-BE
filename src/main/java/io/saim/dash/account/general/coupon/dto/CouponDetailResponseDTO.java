package io.saim.dash.account.general.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponDetailResponseDTO {
	private Long couponId;
	private String couponName;
	private String partnerName;
	private String validUntil;
}
