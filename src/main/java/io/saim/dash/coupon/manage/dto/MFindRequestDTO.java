package io.saim.dash.coupon.manage.dto;

import io.saim.dash.coupon.common.model.IssueLog;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MFindRequestDTO {
	@Getter private final Integer page;
	@Getter private final Integer size;
	private final String vendor_name;
	private final String president_name;
	private final String business_name;
	private final Boolean include_completed;

	public String getVendorName() {
		return vendor_name;
	}

	public String getPresidentName() {
		return president_name;
	}

	public String getBusinessName() {
		return business_name;
	}

	public Boolean getIncludeCompleted() {
		return include_completed;
	}
}
