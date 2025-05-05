package io.saim.dash.coupon.issue.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.issue.dto.IssueConfirmSpecDTO;
import io.saim.dash.coupon.issue.dto.IRSignRequestDTO;
import io.saim.dash.coupon.issue.dto.IRCreateRequestDTO;
import io.saim.dash.coupon.issue.dto.IRResponseDTO;
import io.saim.dash.coupon.common.dto.IssueResultDTO;
import io.saim.dash.coupon.issue.dto.IRSignResponseDTO;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.global.dto.PagingResponse;
import lombok.RequiredArgsConstructor;

import io.saim.dash.coupon.common.model.IssueRequest;
import io.saim.dash.coupon.issue.service.IssueService;

@RestController
@RequestMapping("/coupon/issue")
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@GetMapping("/list")
	public PagingResponse<IRResponseDTO> getIssues(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@RequestParam(required = false, defaultValue = "1") int page,
		@RequestParam(required = false, defaultValue = "10") int size,
		@RequestParam(required = false) String createat_start,
		@RequestParam(required = false) String createat_end,
		@RequestParam(required = false) String business_name,
		@RequestParam(required = false) String owner_phone,
		@RequestParam(required = false) IssueStatus status
	) {
		List<IssueRequest> userIssueRequestList = issueService.getIssueRequestsByUser(
			serviceUser,
			page, size,
			createat_start, createat_end,
			business_name, owner_phone, status
		);

		List<IRResponseDTO> issueRequestList = userIssueRequestList.stream()
			.map(IRResponseDTO::new)
			.toList();

		return new PagingResponse<>(
			page, size,
			issueRequestList
		);
	}

	@GetMapping("/spec/{issueId}")
	public IRResponseDTO getIssueRequestSpec(
		@AuthenticationPrincipal DUMMY_ServiceUser user,
		@PathVariable Long issueId
	) {
		IssueRequest issueRequest = issueService.getIssueRequest(issueId, user);
		return new IRResponseDTO(issueRequest);
	}

	@PostMapping("/create")
	public IRResponseDTO createIssue(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@RequestBody IRCreateRequestDTO IRCreateRequestDTO
	) {
		VendorDTO vendor = IRCreateRequestDTO.getVendor();
		PartnerDTO partner = IRCreateRequestDTO.getPartner();

		IssueRequest issueRequest = issueService.createIssueRequest(
			serviceUser,
			vendor.getVendorName(), vendor.getPresidentName(), vendor.getPresidentPhone(),
			partner.getBusinessName(), partner.getOwnerPhone(),
			IRCreateRequestDTO.getProducts()
		);

		return new IRResponseDTO(issueRequest);
	}

	@PostMapping("/{issueId}/sign")
	public IRSignResponseDTO signIssue(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@PathVariable Long issueId,
		@RequestBody IRSignRequestDTO IRSignRequestDTO
	) {
		IssueResultDTO issueResult = issueService.signIssueRequest(
			serviceUser, issueId,
			IRSignRequestDTO.getStatus(),
			IRSignRequestDTO.getPayment().getPaidAt(),
			IRSignRequestDTO.getPayment().getPrices(),
			IRSignRequestDTO.getPayment().getDiscount()
		);

		IssueStatus updatedStatus = issueResult.getStatus();
		IRSignResponseDTO.IRSignResponseDTOBuilder status = IRSignResponseDTO.builder()
			.result(true)
			.status(updatedStatus);

		if (updatedStatus == IssueStatus.APPROVED)
			status.confirmSpec(new IssueConfirmSpecDTO(issueResult));

		return status.build();
	}

	@DeleteMapping("/{issueId}")
	public Boolean deleteIssue(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@PathVariable Long issueId
	) {
		return issueService.deleteIssueRequest(serviceUser, issueId);
	}
}
