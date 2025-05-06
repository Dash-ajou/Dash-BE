package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;

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

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_id")
	private Issue issue;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private String registerCode;

	@Column(nullable = false)
	private CouponStatus couponStatus = CouponStatus.REGISTERABLE;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private LocalDateTime expiredAt;

	@Builder
	public Coupon(
		Issue issue,
		Product product,
		String registerCode, CouponStatus couponStatus
	) {
		this.issue = issue;
		this.product = product;
		this.registerCode = registerCode;
		this.couponStatus = couponStatus;
	}
}
