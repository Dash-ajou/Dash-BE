package io.saim.dash.account.partner.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizationRequestDetailDTO {
	private String request_detail;
	private int request_count;
	private String total_price;
	private String approval_date;
}
