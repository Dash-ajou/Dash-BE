package io.saim.dash.account.general.coupon.model;

import jakarta.persistence.*;
import lombok.*;
import io.saim.dash.account.partner.model.PartnerUser;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "price", nullable = false)
	private Integer price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false)
	private PartnerUser partner;
}
