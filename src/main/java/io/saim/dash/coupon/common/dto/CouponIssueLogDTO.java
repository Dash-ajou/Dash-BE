package io.saim.dash.coupon.common.dto;

import java.time.LocalDateTime;

import com.querydsl.core.Tuple;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.QIssueLog;
import io.saim.dash.coupon.common.model.QIssueRequest;
import io.saim.dash.coupon.common.model.VendorGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CouponIssueLogDTO {
	private Long requestId;
	private VendorGroup vendorGroup;
	private DUMMY_PartnerUser partner;
	private Long issueId;
	private CouponActiveStatus couponActiveStatus;
	private LocalDateTime issuedAt;
	private Long issueCnt;
	private Long usedCnt;

	public CouponIssueLogDTO(Tuple data) {
		QIssueRequest issueRequest = QIssueRequest.issueRequest;
		QIssueLog issueLog = QIssueLog.issueLog;

		this.requestId = data.get(issueRequest.requestId);
		this.vendorGroup = data.get(issueRequest.vendorGroup);
		this.partner = data.get(issueRequest.partner);
		this.issueId = data.get(issueLog.issuedId);
		this.couponActiveStatus = data.get(issueLog.couponActiveStatus);
		this.issuedAt = data.get(issueLog.decidedAt);
		this.issueCnt = data.get(issueLog.issueCnt);
		this.usedCnt = data.get(issueLog.usedCnt);
	}
}
