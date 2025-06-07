package io.saim.dash.account.general.coupon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponPaymentCodeResponseDTO {
	@JsonProperty("qrcode_image")
	private String qrcodeImage;
	private Long couponId;
	private String paymentCode;
}
