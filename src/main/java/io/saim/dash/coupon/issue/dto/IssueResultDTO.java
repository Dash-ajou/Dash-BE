package io.saim.dash.coupon.issue.dto;

import java.time.LocalDateTime;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.model.IssueLog;

public record IssueResultDTO (
	Issue issue,
	IssueLog issueLog
) {}
