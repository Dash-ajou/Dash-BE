package io.saim.dash.coupon.issue.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.Vendor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class RequestBriefResponseDTO {
	private Long request_id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
