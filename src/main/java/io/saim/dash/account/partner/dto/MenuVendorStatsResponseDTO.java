package io.saim.dash.account.partner.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuVendorStatsResponseDTO {
	private Long vendor_id;
	private String vendor_name;
	private int vendor_issued;
	private int vendor_used;
	private int vendor_remained;
	private String usable_status;
}
