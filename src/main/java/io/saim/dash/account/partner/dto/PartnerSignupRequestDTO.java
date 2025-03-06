package io.saim.dash.account.partner.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PartnerSignupRequestDTO {
	private String partnerName;
	private String partnerAddress;
	private String ownerName;
	private String ownerPhone;
	private String ownerEmail;
	private boolean isTemporary;
	private LocalDateTime temporaryRegisterDate;
}
