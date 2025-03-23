package io.saim.dash.coupon.issue.dto;

import java.util.ArrayList;
import java.util.List;

import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import jakarta.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class IssueCreateRequestDTO {
	private VendorDTO vendor;
	private PartnerDTO partner;
	private List<Long> products = new ArrayList<>();
}
