package io.saim.dash.coupon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Product {

	@Id
	@Column(name="product_id")
	private Long productId;

	@ManyToOne()
	// @Column(name = "partner_id", nullable = false)
	private DUMMY_PartnerUser partner;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(nullable = false)
	private Integer price;

	@Builder
	public Product(DUMMY_PartnerUser partner, String productName, Integer price) {
		this.partner = partner;
		this.productName = productName;
		this.price = price;
	}
}
