package io.saim.dash.coupon.issue.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.common.model.UserType;
import io.saim.dash.account.push.service.PushService;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.dto.Issue.IssueConfirmSpecDTO;
import io.saim.dash.coupon.issue.dto.RequestSignRequestDTO;
import io.saim.dash.coupon.issue.dto.RequestCreateRequestDTO;
import io.saim.dash.coupon.issue.dto.RequestBriefResponseDTO;
import io.saim.dash.coupon.common.dto.Issue.IssueResultDTO;
import io.saim.dash.coupon.issue.dto.RequestSpecResponseDTO;
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
	private final PushService pushService;

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
		ServiceUser loginUser = getLoginUser(customUserDetails);

		List<Request> userRequestList = issueService.getRequests(
			loginUser,
			page, size,
			createat_start, createat_end,
			business_name, owner_phone, status
		);

		ServiceUser finalUser = loginUser;
		List<RequestBriefResponseDTO> issueRequestList = userRequestList.stream()
			.map(request -> new RequestBriefResponseDTO(request, finalUser.isPartner()))
			.toList();

		return new PagingResponse<>(
			page, size,
			issueRequestList
		);
	}

	@GetMapping("/spec/{issueId}")
	public RequestSpecResponseDTO getIssueRequestSpec(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long issueId
	) {
		ServiceUser user = getLoginUser(customUserDetails);

		Request request = issueService.getRequest(issueId, user);
		return new RequestSpecResponseDTO(request, user.isPartner());
	}

	@PostMapping("/request")
	public RequestBriefResponseDTO createRequest(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody RequestCreateRequestDTO RequestCreateRequestDTO
	) {
		ServiceUser user = getLoginUser(customUserDetails);

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

	@GetMapping("/{requestId}/form")
	public ResponseEntity<byte[]> getRegisteredForm(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long requestId
	) {
		ServiceUser user = getLoginUser(customUserDetails);
		byte[] formImage = issueService.getFormImage(user, requestId);

		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_TYPE, "image/png")
			.body(formImage);
	}

	@PostMapping("/{requestId}/form")
	public void registerForm(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long requestId,
		@RequestParam(name = "coupon_format") MultipartFile formFile
	) {
		ServiceUser user = getLoginUser(customUserDetails);
		issueService.registerFormImage(user, requestId, formFile);
	}

	@PostMapping("/{issueId}/sign")
	public SignResponseDTO signIssue(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long issueId,
		@RequestBody RequestSignRequestDTO RequestSignRequestDTO
	) {
		ServiceUser user = getLoginUser(customUserDetails);

		IssueResultDTO issueResult = issueService.signRequest(
			user, issueId,
			RequestSignRequestDTO.getStatus(),
			RequestSignRequestDTO.getPayment()
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
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long issueId
	) {
		ServiceUser user = getLoginUser(customUserDetails);
		return issueService.deleteIssueRequest(user, issueId);
	}

	private static ServiceUser getLoginUser(CustomUserDetails customUserDetails) {
		ServiceUser user;
		try {
			user = customUserDetails.getGeneralUser();
		} catch (Exception e) {
			user = customUserDetails.getPartnerUser();
		}
		user.setUserType(UserType.valueOf(customUserDetails.getUserType()));
		return user;
	}
}
