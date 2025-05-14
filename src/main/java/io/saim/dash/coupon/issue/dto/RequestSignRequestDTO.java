package io.saim.dash.coupon.issue.dto;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.Request.RequestPaymentInfo;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class RequestSignRequestDTO {
	private final IssueStatus status;

	@Nullable
	private final RequestPaymentInfo payment;
}

