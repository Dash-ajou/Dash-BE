package io.saim.dash.coupon.common.dto.Product;

import io.saim.dash.coupon.common.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDTO {
	private Long product_id;
	private Long partner_id;
	private String product_name;
	private Long price;

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
