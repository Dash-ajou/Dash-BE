package io.saim.dash.coupon.payment.dto;

import io.saim.dash.coupon.common.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductBriefDTO {
	private Long productId;
	private Long partnerId;
	private String productName;

	public ProductBriefDTO(Product product) {
		this.productId = product.getProductId();
		this.partnerId = product.getPartner().getId();
		this.productName = product.getProductName();
	}
}
