package io.saim.dash.coupon.issue.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.IssueRequest;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.VendorGroup;

public class IRResponseDTO {
	private Long request_id;
	private LocalDateTime createdAt;
	private VendorGroup vendorGroup;
	private DUMMY_PartnerUser partner;
	private IssueStatus status;
	private List<Product> products = new ArrayList<>();

	public IRResponseDTO(IssueRequest issueRequest) {
		this.request_id = issueRequest.getRequestId();
		this.createdAt = issueRequest.getCreatedAt();
		this.vendorGroup = issueRequest.getVendorGroup();
		this.partner = issueRequest.getPartner();
		this.status = issueRequest.getStatus();
		this.products = issueRequest.getProducts();
	}
}
