package io.saim.dash.coupon.issue.dto;

import io.saim.dash.coupon.common.constant.IssueStatus;
import jakarta.annotation.Nullable;
import lombok.Builder;

public class IssueSignResponseDTO {
	private final Boolean result;
	private final IssueStatus status;

	@Nullable
	private final IssueConfirmSpecDTO confirmSpec;

	@Builder
	public IssueSignResponseDTO(Boolean result, IssueStatus status, @Nullable IssueConfirmSpecDTO confirmSpec) {
		this.result = result;
		this.status = status;
		this.confirmSpec = confirmSpec;
	}
}
