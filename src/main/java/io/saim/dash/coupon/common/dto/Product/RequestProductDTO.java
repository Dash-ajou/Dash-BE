/*
package io.saim.dash.coupon.common.dto.Product;

import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestProductDTO {
	private final Long productId;
	private final Long partnerId;
	private final String productName;
	private final Long price;

	public RequestProductDTO(Product product, RequestProduct requestProduct) {
		this.productId = product.getProductId();
		this.partnerId = product.getPartner().getId();
		this.productName = product.getProductName();

		if (requestProduct != null) {
			this.price = requestProduct.getPrice();
		} else this.price = product.getPrice();
	}
}

 */
