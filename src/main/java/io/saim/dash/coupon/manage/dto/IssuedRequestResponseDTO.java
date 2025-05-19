/*package io.saim.dash.coupon.manage.dto;

import java.time.LocalDateTime;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.dto.Issue.CouponIssueLogDTO;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class IssuedRequestResponseDTO {
	private final Long request_id;
	private final VendorDTO vendor;
	private final PartnerDTO partner;
	private final Long issue_id;
	private final IssueActiveStatus status;
	private final LocalDateTime issue_at;
	private final Long issue_count;
	private final Long used_count;

	public IssuedRequestResponseDTO(CouponIssueLogDTO couponIssueLogDTO) {
		this.request_id = couponIssueLogDTO.getRequestId();
		this.vendor = new VendorDTO(
			couponIssueLogDTO.getVendor().getName(),
			couponIssueLogDTO.getVendor().getPresidentName(),
			couponIssueLogDTO.getVendor().getPresidentPhone()
		);
		this.partner = new PartnerDTO(
			couponIssueLogDTO.getPartner().getPartnerName(),
			couponIssueLogDTO.getPartner().getPhone()
		);
		this.issue_id = couponIssueLogDTO.getIssueId();
		this.status = couponIssueLogDTO.getIssueActiveStatus();
		this.issue_at = couponIssueLogDTO.getIssuedAt();
		this.issue_count = couponIssueLogDTO.getIssueCnt();
		this.used_count = couponIssueLogDTO.getUsedCnt();
	}
}

 */
