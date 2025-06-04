package io.saim.dash.coupon.common.dto.Product;

import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RequestProductBriefDTO {
	private Long product_id;
	private Long count;

	public RequestProductBriefDTO(RequestProduct requestProduct) {
		this.product_id = requestProduct.getProduct().getProductId();
		this.count = requestProduct.getQuantity();
	}
}
