package io.saim.dash.coupon.issue.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.issue.dto.IssueConfirmSpecDTO;
import io.saim.dash.coupon.issue.dto.IssueSignRequestDTO;
import io.saim.dash.coupon.issue.dto.IssueCreateRequestDTO;
import io.saim.dash.coupon.issue.dto.IssueResponseDTO;
import io.saim.dash.coupon.issue.dto.IssueResultDTO;
import io.saim.dash.coupon.issue.dto.IssueSignResponseDTO;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.global.dto.PagingResponse;
import lombok.RequiredArgsConstructor;

import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.issue.service.IssueService;

@RestController
@RequestMapping("/coupon/issue")
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@GetMapping("/list")
	public PagingResponse<IssueResponseDTO> getIssues(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@RequestParam(required = false) int page,
		@RequestParam(required = false) int size,
		@RequestParam(required = false) String createat_start,
		@RequestParam(required = false) String createat_end,
		@RequestParam(required = false) String business_name,
		@RequestParam(required = false) String owner_phone,
		@RequestParam(required = false) IssueStatus status
	) {
		List<Issue> userIssueList = issueService.getIssuesByUser(
			serviceUser,
			page, size,
			createat_start, createat_end,
			business_name, owner_phone, status
		);

		List<IssueResponseDTO> issueResponse = userIssueList.stream()
			.map(IssueResponseDTO::new)
			.toList();

		return new PagingResponse<>(
			page, size,
			issueResponse
		);
	}

	@GetMapping("/spec/{issueId}")
	public IssueResponseDTO getIssueRequestSpec(
		@AuthenticationPrincipal DUMMY_ServiceUser user,
		@PathVariable Long issueId
	) {
		Issue issue = issueService.getIssue(issueId, user);
		return new IssueResponseDTO(issue);
	}

	@PostMapping("/create")
	public IssueResponseDTO createIssue(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@RequestBody IssueCreateRequestDTO issueCreateRequestDTO
	) {
		VendorDTO vendor = issueCreateRequestDTO.getVendor();
		PartnerDTO partner = issueCreateRequestDTO.getPartner();

		Issue issue = issueService.createIssue(
			serviceUser,
			vendor.getVendorName(), vendor.getPresidentName(), vendor.getPresidentPhone(),
			partner.getBusinessName(), partner.getOwnerPhone(),
			issueCreateRequestDTO.getProducts()
		);

		return new IssueResponseDTO(issue);
	}

	@PostMapping("/{issueId}/sign")
	public IssueSignResponseDTO signIssue(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@PathVariable Long issueId,
		@RequestBody IssueSignRequestDTO issueSignRequestDTO
	) {
		IssueResultDTO issueResult = issueService.signIssue(
			serviceUser, issueId,
			issueSignRequestDTO.getStatus(),
			issueSignRequestDTO.getPayment().getPaidAt(),
			issueSignRequestDTO.getPayment().getPrices(),
			issueSignRequestDTO.getPayment().getDiscount()
		);

		IssueStatus updatedStatus = issueResult.issue().getStatus();
		IssueSignResponseDTO.IssueSignResponseDTOBuilder status = IssueSignResponseDTO.builder()
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
		return issueService.deleteIssue(serviceUser, issueId);
	}
}
