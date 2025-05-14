package io.saim.dash.coupon.common.dto.Request;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestPaymentInfo {
	private final String paid_at;
	private final List<RequestProductPriceDTO> prices;
	private final Long discount;

	public String getPaidAt() {
		return paid_at;
	}
}
