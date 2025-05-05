package io.saim.dash.coupon.common.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.IssueRequest;
import io.saim.dash.coupon.common.model.IssueLog;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.VendorGroup;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueResultDTO {

	private final Long requestId;
	private final VendorDTO vendor;
	private final PartnerDTO partner;
	private final List<Product> products;
	private final LocalDateTime createdAt;

	private final Long issueId;
	private final IssueStatus status;
	private final Long paidPrice;
	private final LocalDateTime paidAt;

	private final LocalDateTime issuedAt;
	private final Long issueCount;
	private final List<Coupon> coupons;

	@Builder
	public IssueResultDTO(
		IssueLog issueLog, List<Coupon> coupons
	) {
		IssueRequest issueRequest = issueLog.getIssueRequest();

		VendorGroup vendorGroup = issueRequest.getVendorGroup();
		DUMMY_PartnerUser partnerUser = issueRequest.getPartner();
		this.vendor = new VendorDTO(
			vendorGroup.getName(),
			vendorGroup.getPresidentName(),
			vendorGroup.getPresidentPhone()
		);
		this.partner = new PartnerDTO(
			partnerUser.getPartnerName(),
			partnerUser.getPhone()
		);

		this.requestId = issueRequest.getRequestId();
		this.products = issueRequest.getProducts();
		this.createdAt = issueRequest.getCreatedAt();

		// -----------------------

		this.issueId = issueLog.getIssueId();
		this.status = issueRequest.getStatus();
		this.paidPrice = issueLog.getPaidPrice();
		this.paidAt = issueLog.getPaidAt();

		// -----------------------

		this.issuedAt = issueLog.getDecidedAt();
		this.issueCount = issueLog.getIssueCnt();
		this.coupons = coupons;
	}

	@Builder
	public IssueResultDTO(
		IssueRequest issueRequest
	) {
		this.requestId = issueRequest.getRequestId();

		VendorGroup vendorGroup = issueRequest.getVendorGroup();
		DUMMY_PartnerUser partnerUser = issueRequest.getPartner();
		this.vendor = new VendorDTO(
			vendorGroup.getName(),
			vendorGroup.getPresidentName(),
			vendorGroup.getPresidentPhone()
		);
		this.partner = new PartnerDTO(
			partnerUser.getPartnerName(),
			partnerUser.getPhone()
		);

		this.products = issueRequest.getProducts();
		this.createdAt = issueRequest.getCreatedAt();

		// -----------------------

		this.issueId = null;
		this.status = IssueStatus.DENIED;
		this.paidPrice = null;
		this.paidAt = null;
		this.issuedAt = null;
		this.issueCount = null;
		this.coupons = null;
	}
}
