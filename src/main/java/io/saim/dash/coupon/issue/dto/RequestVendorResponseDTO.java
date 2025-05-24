package io.saim.dash.coupon.issue.dto;

import java.time.LocalDateTime;

import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.Vendor;

public class RequestVendorResponseDTO {
	private Long request_id;
	private LocalDateTime createdAt;
	private Vendor vendor;
	private PartnerUser partner;
	private IssueStatus status;

	public RequestVendorResponseDTO(Request request) {
		this.request_id = request.getRequestId();
		this.createdAt = request.getCreatedAt();
		this.vendor = request.getVendor();
		this.partner = request.getPartner();
		this.status = request.getStatus();
	}
}
