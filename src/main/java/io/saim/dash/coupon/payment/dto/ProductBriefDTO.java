package io.saim.dash.coupon.payment.dto;

import io.saim.dash.coupon.common.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ProductBriefDTO {
	private Long product_id;
	private Long partner_id;
	private String product_name;

	public ProductBriefDTO(Product product) {
		this.product_id = product.getProductId();
		this.partner_id = product.getPartner().getId();
		this.product_name = product.getProductName();
	}
}
