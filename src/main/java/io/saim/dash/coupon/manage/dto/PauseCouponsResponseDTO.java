package io.saim.dash.coupon.manage.dto;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.dto.PauseCouponsResultDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PauseCouponsResponseDTO {
	private final Long issue_id;
	private final Long issue_count;
	private final Long deactive_count;
	private final Long active_count;

	public PauseCouponsResponseDTO(Long issueId, IssueActiveStatus status, PauseCouponsResultDTO pauseCouponsResultDTO) {
		this.issue_id = issueId;
		this.issue_count = pauseCouponsResultDTO.getIssueCount();
		if (status == IssueActiveStatus.DISABLED) {
			this.active_count = null;
			this.deactive_count = pauseCouponsResultDTO.getUpdatedCount();
		} else {
			this.active_count = pauseCouponsResultDTO.getUpdatedCount();
			this.deactive_count = null;
		}
	}
}
