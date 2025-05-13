package io.saim.dash.coupon.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Product {

	@Id
	@Column(name="product_id")
	private Long productId;

	@Column(name = "partner_id", nullable = false)
	private DUMMY_PartnerUser partner;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(nullable = false)
	private Long price;

	@Builder
	public Product(DUMMY_PartnerUser partner, String productName, Long price) {
		this.partner = partner;
		this.productName = productName;
		this.price = price;
	}
}
