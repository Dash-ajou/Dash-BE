package io.saim.dash.coupon.manage.dto;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.dto.Issue.PauseCouponsResultDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
@Builder @Getter
public class PauseCouponsResponseDTO {
	private Long issue_id;
	private Long issue_count;
	private Long deactive_count;
	private Long active_count;

	public PauseCouponsResponseDTO(Long issueId, IssueActiveStatus status, PauseCouponsResultDTO pauseCouponsResultDTO) {
		this.issue_id = issueId;
		this.issue_count = pauseCouponsResultDTO.getIssueCount();
		if (status == IssueActiveStatus.DISABLE) {
			this.active_count = null;
			this.deactive_count = pauseCouponsResultDTO.getUpdatedCount();
		} else {
			this.active_count = pauseCouponsResultDTO.getUpdatedCount();
			this.deactive_count = null;
		}
	}
}
