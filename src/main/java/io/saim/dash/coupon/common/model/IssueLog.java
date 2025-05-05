package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssueLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long issuedId;

	private CouponActiveStatus couponActiveStatus;

	@OneToOne
	private IssueRequest issueRequest;
	private Long issueCnt;
	private Long usedCnt;

	@Nullable
	private LocalDateTime paidAt = LocalDateTime.now();
	private LocalDateTime decidedAt;
	private Long paidPrice;

	@Builder
	public IssueLog(CouponActiveStatus couponActiveStatus, IssueRequest issueRequest, Long issueCnt, Long usedCnt, LocalDateTime paidAt,
		LocalDateTime decidedAt, Long paidPrice) {
		this.issueRequest = issueRequest;
		this.decidedAt = decidedAt;
		this.paidAt = paidAt;
		this.paidPrice = paidPrice;
		this.issueCnt = issueCnt;
		this.usedCnt = usedCnt;
		this.couponActiveStatus = couponActiveStatus;
	}
}
