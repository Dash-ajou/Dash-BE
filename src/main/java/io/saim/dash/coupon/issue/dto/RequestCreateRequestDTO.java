package io.saim.dash.coupon.issue.dto;

import java.util.ArrayList;
import java.util.List;

import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.Request.RequestProductCountDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RequestCreateRequestDTO {
	private VendorDTO vendor;
	private PartnerDTO partner;
	private List<RequestProductCountDTO> products = new ArrayList<>();
}
