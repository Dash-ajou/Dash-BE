package io.saim.dash.account.general.coupon.model;

import io.saim.dash.account.partner.model.PartnerUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coupon_request")
public class CouponRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coupon_request_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false)
	private PartnerUser partner;

	@Column(name = "request_detail", nullable = false)
	private String requestDetail;

	@Column(name = "request_count", nullable = false)
	private Integer requestCount;

	@Column(name = "total_price", nullable = false)
	private Integer totalPrice;

	@Column(name = "approval_date", nullable = false)
	private LocalDateTime approvalDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
}
