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
@Table(name = "coupon_payment_code")
public class CouponPaymentCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_code_id")
	private Long paymentCodeId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_management_id", nullable = false)
	private Coupon coupon;

	@Column(name = "qr_code_url", nullable = false)
	private String qrCodeUrl;

	@Column(name = "issued_at", nullable = false)
	private LocalDateTime issuedAt;

	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;
}
