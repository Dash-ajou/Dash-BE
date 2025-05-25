package io.saim.dash.coupon.product.dto;

import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class ProductFindResponseDTO {
	private final Long product_id;
	private final Long partner_id;
	private final String product_name;
	private final Long price;

	public ProductFindResponseDTO(RequestProductDTO requestProductDTO) {
		this.product_id = requestProductDTO.getProductId();
		this.partner_id = requestProductDTO.getPartnerId();
		this.product_name = requestProductDTO.getProductName();
		this.price = requestProductDTO.getPrice();
	}
}
