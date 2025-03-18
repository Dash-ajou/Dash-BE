package io.saim.dash.account.general.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponTransferResponseDTO {
	private String receiverEmail;  //양도받는 사람 이메일
	private String couponName;     //양도된 쿠폰 이름
}
