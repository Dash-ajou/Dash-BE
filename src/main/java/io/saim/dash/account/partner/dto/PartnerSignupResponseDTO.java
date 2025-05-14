package io.saim.dash.account.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PartnerSignupResponseDTO {
	private Long partnerId;
	private String partnerName;
	private String partnerAddress;
	private boolean isTemporary;
	private String temporaryRegisterDate;
}
