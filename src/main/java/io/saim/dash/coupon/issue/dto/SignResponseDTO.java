package io.saim.dash.coupon.issue.dto;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.Issue.IssueConfirmSpecDTO;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class SignResponseDTO {
	private Boolean result;
	private IssueStatus status;

	@Nullable
	private IssueConfirmSpecDTO confirmSpec;

	@Builder
	public SignResponseDTO(Boolean result, IssueStatus status, @Nullable IssueConfirmSpecDTO confirmSpec) {
		this.result = result;
		this.status = status;
		this.confirmSpec = confirmSpec;
	}
}
