package io.saim.dash.account.general.coupon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsedCouponResponseDTO {
	private Long couponId;
	private String couponName;
	private String paymentCode;
	private Long paymentId;
	private String partnerName;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime usedAt;
}
