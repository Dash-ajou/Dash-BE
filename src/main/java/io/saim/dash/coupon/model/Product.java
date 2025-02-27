package io.saim.dash.coupon.model;

import io.saim.dash.coupon.common.dummy.PartnerUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

	@Id
	@Column(name="product_id")
	private Long productId;

	@ManyToOne
	@Column(name = "partner_id", nullable = false)
	private PartnerUser partner;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(nullable = false)
	private Integer price;
}
