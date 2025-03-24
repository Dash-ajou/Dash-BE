package io.saim.dash.coupon.issue.dto;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;

public class IssueConfirmSpecDTO {
	private final Long issued_id;
	private final Long issue_id;
	private final String paid_at;
	private final CouponActiveStatus status;
	private final Long issue_count;
	private final Long paid_price;
	private final String issued_at;

	public IssueConfirmSpecDTO(IssueResultDTO issueResultDTO) {
		this.issued_id = issueResultDTO.issueLog().getIssuedId();
		this.issue_id = issueResultDTO.issueRequest().getRequestId();
		this.paid_at = issueResultDTO.issueLog().getPaidAt().toString();
		this.status = issueResultDTO.issueLog().getCouponActiveStatus();
		this.issue_count = issueResultDTO.issueLog().getIssueCnt();
		this.paid_price = issueResultDTO.issueLog().getPaidPrice();
		this.issued_at = issueResultDTO.issueLog().getDecidedAt().toString();
	}
}
