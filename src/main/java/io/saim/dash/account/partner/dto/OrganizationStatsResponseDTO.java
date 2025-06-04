package io.saim.dash.account.partner.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrganizationStatsResponseDTO {
	private String vendor_name;
	private String head_name;
	private String head_contact;
	private List<OrganizationRequestDetailDTO> details;
}
