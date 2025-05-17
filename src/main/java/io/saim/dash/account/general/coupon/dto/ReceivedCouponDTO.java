package io.saim.dash.account.general.coupon.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivedCouponDTO {

	private Long couponId;
	private String couponName;
	private String partnerName;
	private String validUntil;
	private String couponStatus;
}
