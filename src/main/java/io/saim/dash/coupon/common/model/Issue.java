package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long issueId;

	@Enumerated(EnumType.STRING)
	@Setter
	@Column(name = "issue_active_status")
	private IssueActiveStatus issueActiveStatus;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "request_id")
	private Request request;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id", nullable = false)
	private Vendor vendor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false)
	private PartnerUser partner;

	@OneToMany(
		mappedBy = "issue", fetch = FetchType.LAZY,
		cascade = CascadeType.PERSIST
	)
	private List<Coupon> coupons = new ArrayList<>();

	@Setter
	private Long issueCnt;

	private Long usedCnt = 0L;

	private Long registerCnt = 0L;

	@Nullable
	private LocalDateTime paidAt = LocalDateTime.now();
	private Long paidPrice;

	@CreatedDate
	private LocalDateTime decidedAt = LocalDateTime.now();

	@Builder
	public Issue(IssueActiveStatus issueActiveStatus, Request request, Long issueCnt, Long usedCnt, Long registerCnt, LocalDateTime paidAt,
		Long paidPrice, Vendor vendor, PartnerUser partner) {
		this.request = request;
		this.paidAt = paidAt;
		this.paidPrice = paidPrice;
		this.issueCnt = issueCnt;
		this.usedCnt = usedCnt;
		this.registerCnt = registerCnt;
		this.issueActiveStatus = issueActiveStatus;
		this.vendor = vendor;
		this.partner = partner;
		this.decidedAt = LocalDateTime.now();
	}

	public Long increaseUsedCnt() {
		return ++this.usedCnt;
	}

	public Long decreaseUsedCnt() {
		return --this.usedCnt;
	}

	public Long increaseRegisterCnt() {
		return ++this.registerCnt;
	}

	public Long decreaseRegisterCnt() {
		return --this.registerCnt;
	}

	public void addCoupons(List<Coupon> issuedCoupons) {
		this.coupons.addAll(issuedCoupons);
	}
}
