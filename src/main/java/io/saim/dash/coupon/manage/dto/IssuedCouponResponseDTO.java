package io.saim.dash.coupon.manage.dto;

import java.time.LocalDateTime;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import io.saim.dash.coupon.common.dto.CouponIssueLogDTO;
import io.saim.dash.coupon.common.dto.IssueResultDTO;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class IssuedCouponResponseDTO {
	private final Long request_id;
	private final VendorDTO vendor;
	private final PartnerDTO partner;
	private final Long issue_id;
	private final CouponActiveStatus status;
	private final LocalDateTime issue_at;
	private final Long issue_count;
	private final Long used_count;

	public IssuedCouponResponseDTO(CouponIssueLogDTO couponIssueLogDTO) {
		this.request_id = couponIssueLogDTO.getRequestId();
		this.vendor = new VendorDTO(
			couponIssueLogDTO.getVendorGroup().getName(),
			couponIssueLogDTO.getVendorGroup().getPresidentName(),
			couponIssueLogDTO.getVendorGroup().getPresidentPhone()
		);
		this.partner = new PartnerDTO(
			couponIssueLogDTO.getPartner().getPartnerName(),
			couponIssueLogDTO.getPartner().getPhone()
		);
		this.issue_id = couponIssueLogDTO.getIssueId();
		this.status = couponIssueLogDTO.getCouponActiveStatus();
		this.issue_at = couponIssueLogDTO.getIssuedAt();
		this.issue_count = couponIssueLogDTO.getIssueCnt();
		this.used_count = couponIssueLogDTO.getUsedCnt();
	}
}
