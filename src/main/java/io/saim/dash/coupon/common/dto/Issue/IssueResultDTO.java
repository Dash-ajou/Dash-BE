package io.saim.dash.coupon.common.dto.Issue;

import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.Issue;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueResultDTO {

	private Issue issue;
	private Request requets;

	@Builder
	public IssueResultDTO(Issue issue) {
		this.issue = issue;
		this.requets = issue.getRequest();
	}

	@Builder
	public IssueResultDTO(Request request) {
		this.requets = request;
	}
}
