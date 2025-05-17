package io.saim.dash.account.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class VendorDetailInfoDTO {
	private String vendorName;
	private String headName;
	private String headContact;
	private List<RequestDetailDTO> details;
}
