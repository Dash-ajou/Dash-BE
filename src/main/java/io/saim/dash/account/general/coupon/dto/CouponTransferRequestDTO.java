package io.saim.dash.account.general.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponTransferRequestDTO {
	private String receiverEmail;
}
