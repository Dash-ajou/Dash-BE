package io.saim.dash.coupon.issue.dto;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import io.saim.dash.coupon.common.dto.IssueResultDTO;

public class IssueConfirmSpecDTO {
	private final Long request_id;
	private final Long issue_id;
	private final String paid_at;
	private final CouponActiveStatus status;
	private final Long issue_count;
	private final Long paid_price;
	private final String issued_at;

	public IssueConfirmSpecDTO(IssueResultDTO issueResultDTO) {
		this.request_id = issueResultDTO.getRequestId();
		this.issue_id = issueResultDTO.getIssueId();
		this.paid_at = issueResultDTO.getPaidAt().toString();
		this.status = CouponActiveStatus.ENABLED;
		this.issue_count = issueResultDTO.getIssueCount();
		this.paid_price = issueResultDTO.getPaidPrice();
		this.issued_at = issueResultDTO.getIssuedAt().toString();
	}
}
