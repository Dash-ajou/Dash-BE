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
	@Column(name = "delivery_id")
	private Long deliveryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id", nullable = false)
	private Coupon coupon;

	@Column(name = "sender_id", nullable = false)
	private Long senderId;

	@Column(name = "receiver_id", nullable = false)
	private Long receiverId;

	@Column(name = "requested_at", nullable = false)
	private LocalDateTime requestedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private DeliveryStatus status;

	public enum DeliveryStatus {
		PENDING, COMPLETED, FAILED
	}
}
