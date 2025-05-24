package io.saim.dash.coupon.common.model;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.constant.RedeemStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 쿠폰 사용 처리(결제) 로그 엔티티
 */
@Entity
@Table(name = "redeem_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RedeemLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "redeem_id")
	private Long redeemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_code", referencedColumnName = "payment_code_id", nullable = false)
	private CouponPaymentCode payment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private GeneralUser member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false)
	private PartnerUser partner;

	@Column(name = "used_at", nullable = false)
	private LocalDateTime usedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 10, nullable = false)
	private RedeemStatus status;

	// 추가 도메인 로직 필요 시 메서드 정의
}
