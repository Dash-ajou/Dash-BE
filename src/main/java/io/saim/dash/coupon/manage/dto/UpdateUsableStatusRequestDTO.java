package io.saim.dash.coupon.manage.dto;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class UpdateUsableStatusRequestDTO {
	private IssueActiveStatus status;
}
