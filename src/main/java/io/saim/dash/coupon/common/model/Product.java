package io.saim.dash.coupon.common.model;

import io.saim.dash.account.partner.model.PartnerUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Product {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
	private Long productId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partner_id", nullable = false)
	private PartnerUser partner;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(nullable = false)
	private Long price;

	@Builder
	public Product(PartnerUser partner, String productName, Long price) {
		this.partner = partner;
		this.productName = productName;
		this.price = price;
	}
}
