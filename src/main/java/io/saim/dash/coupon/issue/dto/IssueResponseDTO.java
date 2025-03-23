package io.saim.dash.coupon.issue.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.model.Product;
import io.saim.dash.coupon.model.VendorGroup;

public class IssueResponseDTO {
	private Long id;
	private LocalDateTime createdAt;
	private VendorGroup vendorGroup;
	private DUMMY_PartnerUser partner;
	private IssueStatus status;
	private List<Product> products = new ArrayList<>();

	public IssueResponseDTO(Issue issue) {
		this.id = issue.getIssueId();
		this.createdAt = issue.getCreatedAt();
		this.vendorGroup = issue.getVendorGroup();
		this.partner = issue.getPartner();
		this.status = issue.getStatus();
		this.products = issue.getProducts();
	}
}
