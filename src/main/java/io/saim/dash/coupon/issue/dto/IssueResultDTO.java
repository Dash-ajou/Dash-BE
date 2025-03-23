package io.saim.dash.coupon.issue.dto;

import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.IssueLog;

public record IssueResultDTO (
	Issue issue,
	IssueLog issueLog
) {}
