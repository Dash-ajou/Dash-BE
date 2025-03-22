package io.saim.dash.coupon.issue.dto;

import org.springframework.beans.InvalidPropertyException;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.global.dto.PageOptions;

public record IssueFilter(
	int page, int size,
	String createAtStart, String createAtEnd,
	String ownerPhone, IssueStatus issueStatus
) {
	public PageOptions getPageOptions() {
		return new PageOptions(page, size);
	}

	public int getOffset() throws IllegalArgumentException {
		if (page <= 0)
			throw new IllegalArgumentException("page must be greater than zero");

		return (page - 1) * size;
	}

	public int getLimit() {
		return size;
	}
}
