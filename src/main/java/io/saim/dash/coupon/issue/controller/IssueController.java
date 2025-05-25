package io.saim.dash.coupon.issue.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.dto.Issue.IssueConfirmSpecDTO;
import io.saim.dash.coupon.issue.dto.RequestSignRequestDTO;
import io.saim.dash.coupon.issue.dto.RequestCreateRequestDTO;
import io.saim.dash.coupon.issue.dto.RequestBriefResponseDTO;
import io.saim.dash.coupon.common.dto.Issue.IssueResultDTO;
import io.saim.dash.coupon.issue.dto.SignResponseDTO;
import io.saim.dash.global.dto.PagingResponse;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

import io.saim.dash.coupon.issue.service.IssueService;

@RestController
@RequestMapping("/coupon/issue")
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@GetMapping("/list")
	public PagingResponse<RequestBriefResponseDTO> getIssues(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestParam(required = false, defaultValue = "1") int page,
		@RequestParam(required = false, defaultValue = "10") int size,
		@RequestParam(required = false) String createat_start,
		@RequestParam(required = false) String createat_end,
		@RequestParam(required = false) String business_name,
		@RequestParam(required = false) String owner_phone,
		@RequestParam(required = false) IssueStatus status
	) {
		ServiceUser user = customUserDetails.getGeneralUser();
		if (user == null) user = customUserDetails.getPartnerUser();

		List<Request> userRequestList = issueService.getRequestsByPartner(
			user,
			page, size,
			createat_start, createat_end,
			business_name, owner_phone, status
		);

		ServiceUser finalUser = user;
		List<RequestBriefResponseDTO> issueRequestList = userRequestList.stream()
			.map(request -> new RequestBriefResponseDTO(request, finalUser.isPartner()))
			.toList();

		return new PagingResponse<>(
			page, size,
			issueRequestList
		);
	}

	@GetMapping("/spec/{issueId}")
	public RequestBriefResponseDTO getIssueRequestSpec(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long issueId
	) {
		ServiceUser user = customUserDetails.getGeneralUser();
		if (user == null) user = customUserDetails.getPartnerUser();

		Request request = issueService.getRequest(issueId, user);
		return new RequestBriefResponseDTO(request, user.isPartner());
	}

	@PostMapping("/request")
	public RequestBriefResponseDTO createRequest(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody RequestCreateRequestDTO RequestCreateRequestDTO
	) {
		ServiceUser user = customUserDetails.getGeneralUser();
		if (user == null) user = customUserDetails.getPartnerUser();

		VendorDTO vendor = RequestCreateRequestDTO.getVendor();
		PartnerDTO partner = RequestCreateRequestDTO.getPartner();

		Request request = issueService.createRequest(
			user,
			vendor.getVendorName(), vendor.getPresidentName(), vendor.getPresidentPhone(),
			partner.getBusinessName(), partner.getOwnerPhone(),
			RequestCreateRequestDTO.getProducts()
		);

		return new RequestBriefResponseDTO(request, user.isPartner());
	}

	@PostMapping("/{issueId}/sign")
	public SignResponseDTO signIssue(
		@AuthenticationPrincipal ServiceUser serviceUser,
		@PathVariable Long issueId,
		@RequestBody RequestSignRequestDTO RequestSignRequestDTO
	) {
		IssueResultDTO issueResult = issueService.signRequest(
			serviceUser, issueId,
			RequestSignRequestDTO.getStatus(),
			RequestSignRequestDTO.getPayment().getPaidAt(),
			RequestSignRequestDTO.getPayment().getPrices(),
			RequestSignRequestDTO.getPayment().getDiscount()
		);

		IssueStatus updatedStatus = issueResult.getRequets().getStatus();
		SignResponseDTO.SignResponseDTOBuilder status = SignResponseDTO.builder()
			.result(true)
			.status(updatedStatus);

		if (updatedStatus == IssueStatus.APPROVED)
			status.confirmSpec(new IssueConfirmSpecDTO(issueResult));

		return status.build();
	}

	@DeleteMapping("/{issueId}")
	public Boolean deleteIssue(
		@AuthenticationPrincipal ServiceUser serviceUser,
		@PathVariable Long issueId
	) {
		return issueService.deleteIssueRequest(serviceUser, issueId);
	}
}
