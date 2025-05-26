package io.saim.dash.coupon.common.dto.Product;

import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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

		if (requestProduct != null) {
			this.price = requestProduct.getPrice();
		} else this.price = product.getPrice();
	}

	public RequestProductDTO(Long productId, Long partnerId, String productName, Long price, Long count) {
		this.productId = productId;
		this.partnerId = partnerId;
		this.productName = productName;
		this.price = price;
		this.count = count;
	}
}
