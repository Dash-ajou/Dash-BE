package io.saim.dash.coupon.common.dto.Request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestProductCountDTO {
	private final Long product_id;
	private final Long count;

	public Long getProductId() {
		return product_id;
	}
}
