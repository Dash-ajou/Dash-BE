package io.saim.dash.coupon.common.dto.Product;

import io.saim.dash.coupon.common.model.Product;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductDTO {
	private final Long product_id;
	private final Long partner_id;
	private final String product_name;
	private final Long price;

	public ProductDTO(RequestProductDTO requestProductDTO) {
		this.product_id = requestProductDTO.getProductId();
		this.partner_id = requestProductDTO.getPartnerId();
		this.product_name = requestProductDTO.getProductName();
		this.price = requestProductDTO.getPrice();
	}

	public ProductDTO(Product product) {
		this.product_id = product.getProductId();
		this.partner_id = product.getPartner().getId();
		this.product_name = product.getProductName();
		this.price = product.getPrice();
	}
}
