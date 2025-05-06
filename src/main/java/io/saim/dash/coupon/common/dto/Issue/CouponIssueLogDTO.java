package io.saim.dash.coupon.common.dto.Issue;

import java.time.LocalDateTime;

import com.querydsl.core.Tuple;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.QIssue;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.model.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CouponIssueLogDTO {
	private Long requestId;
	private Vendor vendor;
	private DUMMY_PartnerUser partner;
	private Long issueId;
	private CouponActiveStatus couponActiveStatus;
	private LocalDateTime issuedAt;
	private Long issueCnt;
	private Long usedCnt;

	public CouponIssueLogDTO(Tuple data) {
		QRequest issueRequest = QRequest.request;
		QIssue issueLog = QIssue.issue;

		this.requestId = data.get(issueRequest.requestId);
		this.vendor = data.get(issueRequest.vendor);
		this.partner = data.get(issueRequest.partner);
		this.issueId = data.get(issueLog.issueId);
		this.couponActiveStatus = data.get(issueLog.couponActiveStatus);
		this.issuedAt = data.get(issueLog.decidedAt);
		this.issueCnt = data.get(issueLog.issueCnt);
		this.usedCnt = data.get(issueLog.usedCnt);
	}
}
