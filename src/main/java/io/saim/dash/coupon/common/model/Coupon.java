package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CurrentTimestamp;

import io.saim.dash.coupon.common.constant.CouponStatus;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long couponId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_id")
	private Issue issue;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private String registrationCode;

	@Column(nullable = false) @Setter
	private CouponStatus couponStatus = CouponStatus.REGISTERABLE;

	@Column(nullable = false)
	private Long price;

	@CurrentTimestamp
	private LocalDateTime createdAt = LocalDateTime.now();

	// temp: 모든 발행쿠폰 유효기간 1개월로 설정
	@Column(nullable = false)
	private LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);

	@Builder
	public Coupon(
		Issue issue,
		Product product,
		String registrationCode, CouponStatus couponStatus,
		Long price, LocalDateTime expiredAt
	) {
		this.issue = issue;
		this.product = product;
		this.registrationCode = registrationCode;
		this.couponStatus = couponStatus;
		this.price = price;
		this.expiredAt = expiredAt;
	}
}
