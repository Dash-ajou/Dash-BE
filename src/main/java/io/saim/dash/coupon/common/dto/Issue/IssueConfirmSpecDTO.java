/*
package io.saim.dash.coupon.common.dto.Issue;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Request;

public class IssueConfirmSpecDTO {
	private final Long request_id;
	private final Long issue_id;
	private final String paid_at;
	private final IssueActiveStatus status;
	private final Long issue_count;
	private final Long paid_price;
	private final String issued_at;

	public IssueConfirmSpecDTO(IssueResultDTO issueResultDTO) {
		Issue issue = issueResultDTO.getIssue();
		Request request = issue.getRequest();

		this.request_id = request.getRequestId();
		this.issue_id = issue.getIssueId();
		this.paid_at = issue.getPaidAt().toString();
		this.status = issue.getIssueActiveStatus();
		this.issue_count = issue.getIssueCnt();
		this.paid_price = issue.getPaidPrice();
		this.issued_at = issue.getDecidedAt().toString();
	}
}

 */
