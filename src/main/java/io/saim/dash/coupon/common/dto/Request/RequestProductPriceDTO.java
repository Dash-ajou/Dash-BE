package io.saim.dash.coupon.common.dto.Request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestProductPriceDTO {
	private final Long product_id;
	private final Long price;

	public Long getProductId() {
		return product_id;
	}
}
