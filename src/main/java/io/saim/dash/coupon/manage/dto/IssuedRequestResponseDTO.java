package io.saim.dash.coupon.manage.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.dto.Issue.CouponIssueLogDTO;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class IssuedRequestResponseDTO {
	private Long request_id;
	private VendorDTO vendor;
	private PartnerDTO partner;
	private Long issue_id;
	private IssueActiveStatus status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String issue_at;

	private Long issue_count = 0L;
	private Long used_count = 0L;

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
		this.issue_at = couponIssueLogDTO.getIssuedAt().toString();
		this.issue_count = couponIssueLogDTO.getIssueCnt();
		this.used_count = couponIssueLogDTO.getUsedCnt();
	}
}
