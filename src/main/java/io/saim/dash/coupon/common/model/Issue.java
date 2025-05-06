package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long issueId;

	@Embedded
	private CouponActiveStatus couponActiveStatus;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "request_id")
	private Request request;

	@OneToMany(
		mappedBy = "issue", fetch = FetchType.LAZY,
		cascade = CascadeType.PERSIST
	)
	private List<Coupon> coupons = new ArrayList<>();

	private Long issueCnt;
	private Long usedCnt = 0L;

	@Nullable
	private LocalDateTime paidAt = LocalDateTime.now();
	private Long paidPrice;

	@CreatedDate
	private LocalDateTime decidedAt;

	@Builder
	public Issue(CouponActiveStatus couponActiveStatus, Request request, Long issueCnt, Long usedCnt, LocalDateTime paidAt,
		Long paidPrice) {
		this.request = request;
		this.paidAt = paidAt;
		this.paidPrice = paidPrice;
		this.issueCnt = issueCnt;
		this.usedCnt = usedCnt;
		this.couponActiveStatus = couponActiveStatus;
	}
}
