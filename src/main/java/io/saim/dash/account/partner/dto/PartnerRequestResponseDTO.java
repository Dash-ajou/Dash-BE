package io.saim.dash.account.partner.dto;

import io.saim.dash.account.partner.model.PartnerRequest;

public class PartnerRequestResponseDTO {

	private Long id;
	private String partnerName;
	private String requestStatus;

	public PartnerRequestResponseDTO(PartnerRequest entity) {
		this.id = entity.getId();
		this.partnerName = entity.getPartnerName();
		this.requestStatus = entity.getRequestStatus().name();
	}
}
