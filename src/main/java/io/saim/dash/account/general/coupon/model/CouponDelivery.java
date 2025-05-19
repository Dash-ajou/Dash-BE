package io.saim.dash.account.general.coupon.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coupon_delivery")
public class CouponDelivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deliveryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_management_id", nullable = false)
	private Coupon coupon;

	@Column(nullable = false)
	private Long senderId;

	@Column(nullable = false)
	private Long receiverId;

	@Column(nullable = false)
	private LocalDateTime requestedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryStatus status;

	public enum DeliveryStatus {
		PENDING, COMPLETED, REJECTED
	}
}
