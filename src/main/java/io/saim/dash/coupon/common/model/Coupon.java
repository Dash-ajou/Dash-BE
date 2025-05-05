package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;

import io.saim.dash.coupon.common.constant.CouponStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne
	@JoinColumn(name = "issue_id")
	private IssueLog issueLog;

	@Column(nullable = false)
	private Long productId;

	@Column(nullable = false)
	private String registerCode;

	@Column(nullable = false)
	private CouponStatus couponStatus;

	@Column(nullable = false)
	private LocalDateTime expiredAt;

	@Builder
	public Coupon(IssueLog issueLog, Long productId, String registerCode, CouponStatus couponStatus) {
		this.issueLog = issueLog;
		this.productId = productId;
		this.registerCode = registerCode;
		this.couponStatus = couponStatus;
	}
}
