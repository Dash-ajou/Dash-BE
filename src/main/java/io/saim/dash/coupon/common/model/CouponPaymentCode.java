package io.saim.dash.coupon.common.model;

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
	@JoinColumn(name = "coupon_management_id", nullable = false, unique = true)
	private Coupon coupon;

	@Column(name ="payment_code", unique = true, nullable = false)
	private String paymentCode;

	@OneToOne(mappedBy = "paymentCode", fetch = FetchType.LAZY)
	private CouponPaymentLog paymentLog;

	@Column(name = "qr_code_image", nullable = false)
	private String qrCodeImage;

	@Column(name = "issued_at", nullable = false)
	private LocalDateTime issuedAt;

	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;
}
