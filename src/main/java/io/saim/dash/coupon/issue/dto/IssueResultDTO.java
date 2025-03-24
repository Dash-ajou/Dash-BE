package io.saim.dash.coupon.issue.dto;

import io.saim.dash.coupon.common.model.IssueRequest;
import io.saim.dash.coupon.common.model.IssueLog;

public record IssueResultDTO (
	IssueRequest issueRequest,
	IssueLog issueLog
) {}
