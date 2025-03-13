package io.saim.dash.account.general.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponPaymentCodeResponseDTO {
	private String qrcodeUrl;
	private Long couponId; //결제 코드가 발급된 쿠폰 ID
}
