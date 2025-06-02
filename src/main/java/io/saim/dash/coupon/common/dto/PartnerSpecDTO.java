package io.saim.dash.coupon.common.dto;

import io.saim.dash.account.partner.model.PartnerUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter @Builder
public class PartnerSpecDTO {
	private Long id;
	private String business_name;
	private String owner_name;
	private String owner_phone;
	private String owner_email;
	private String address;

	public PartnerSpecDTO(PartnerUser partnerUser) {
		this.id = partnerUser.getId();
		this.business_name = partnerUser.getPartnerName();
		this.owner_name = partnerUser.getOwnerName();
		this.owner_phone = partnerUser.getPhone();
		this.owner_email = partnerUser.getEmail();
		this.address = partnerUser.getPartnerAddress();
	}
}
