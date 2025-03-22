package io.saim.dash.coupon.issue.dto;

import org.springframework.web.bind.annotation.RequestParam;

import io.saim.dash.coupon.common.constant.IssueStatus;

public record IssueListRequestDTO(
	int page, int size,
	String createat_start, String createat_end,
	String business_name, String owner_phone, IssueStatus status
) {
	public IssueListRequestDTO(
		@RequestParam(required = false) int page,
		@RequestParam(required = false) int size,
		@RequestParam(required = false) String createat_start,
		@RequestParam(required = false) String createat_end,
		@RequestParam(required = false) String business_name,
		@RequestParam(required = false) String owner_phone,
		@RequestParam(required = false) IssueStatus status
	) {
		this.page = Math.max(page, 1);
		this.size = Math.max(size, 1);
		this.createat_start = createat_start;
		this.createat_end = createat_end;
		this.business_name = business_name;
		this.owner_phone = owner_phone;
		this.status = status;
	}

	public IssueFilter toIssueFilter() {
		return new IssueFilter(
			page, size,
			createat_start, createat_end,
			owner_phone, status
		);
	}
}
