package io.saim.dash.coupon.common.dto.Product;

import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter @Builder
public class RequestProductDTO {
	private Long productId;
	private Long partnerId;
	private String productName;
	private Long price;
	private Long count;

	public RequestProductDTO(Product product, RequestProduct requestProduct) {
		this.productId = product.getProductId();
		this.partnerId = product.getPartner().getId();
		this.productName = product.getProductName();
		this.price = product.getPrice();

		if (requestProduct != null) {
			this.count = requestProduct.getQuantity();
			this.price = requestProduct.getPrice();
		}
	}
}
