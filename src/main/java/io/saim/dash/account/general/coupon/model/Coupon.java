package io.saim.dash.account.general.coupon.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coupon_management")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coupon_management_id")
	private Long couponId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Enumerated(EnumType.STRING)
	@Column(name = "coupon_status", nullable = false)
	private CouponStatus couponStatus;

	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;

	public enum CouponStatus {
		ACTIVE, EXPIRED, USED
	}
}
