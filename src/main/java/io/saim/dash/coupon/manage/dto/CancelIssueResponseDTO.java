/*package io.saim.dash.coupon.manage.dto;

import io.saim.dash.coupon.common.dto.Issue.CancelIssueResultDTO;
import lombok.Getter;

@Getter
public class CancelIssueResponseDTO {
	private final Long request_id;
	private final Long issue_id;
	private final Long issue_count;
	private final Long deactivate_count;

	public CancelIssueResponseDTO(CancelIssueResultDTO cancelIssueResultDTO) {
		this.request_id = cancelIssueResultDTO.getIssue().getRequest().getRequestId();
		this.issue_id = cancelIssueResultDTO.getIssue().getIssueId();
		this.issue_count = cancelIssueResultDTO.getIssue().getIssueCnt();
		this.deactivate_count = cancelIssueResultDTO.getExpiredCnt();
	}
}


 */
