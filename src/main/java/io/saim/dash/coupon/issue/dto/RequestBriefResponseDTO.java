package io.saim.dash.coupon.issue.dto;

import java.time.LocalDateTime;

import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.Vendor;

public class RequestBriefResponseDTO {
	private Long request_id;
	private LocalDateTime created_at;
	private Vendor vendor;
	private PartnerUser partner;
	private IssueStatus status;

	public RequestBriefResponseDTO(Request request, Boolean isPartnerResponse) {
		if (!isPartnerResponse) this.partner = request.getPartner();

		this.request_id = request.getRequestId();
		this.created_at = request.getCreatedAt();
		this.vendor = request.getVendor();
		this.status = request.getStatus();
	}
}
