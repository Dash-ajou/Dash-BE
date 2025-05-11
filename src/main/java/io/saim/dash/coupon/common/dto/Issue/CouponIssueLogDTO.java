package io.saim.dash.coupon.common.dto.Issue;

import java.time.LocalDateTime;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CouponIssueLogDTO {
	private Long requestId;
	private Vendor vendor;
	private DUMMY_PartnerUser partner;
	private Long issueId;
	private IssueActiveStatus issueActiveStatus;
	private LocalDateTime issuedAt;
	private Long issueCnt;
	private Long usedCnt;

	public CouponIssueLogDTO(Issue issue) {
		this.requestId = issue.getRequest().getRequestId();
		this.vendor = issue.getRequest().getVendor();
		this.partner = issue.getRequest().getPartner();
		this.issueId = issue.getIssueId();
		this.issueActiveStatus = issue.getIssueActiveStatus();
		this.issuedAt = issue.getDecidedAt();
		this.issueCnt = issue.getIssueCnt();
		this.usedCnt = issue.getUsedCnt();
	}
}
