package io.saim.dash.coupon.manage.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.auth.service.PhoneVerificationService;
import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.common.model.UserType;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.constant.CouponExportType;
import io.saim.dash.coupon.common.dto.Coupon.CouponBriefDTO;
import io.saim.dash.coupon.common.dto.Issue.CancelIssueResultDTO;
import io.saim.dash.coupon.common.dto.Issue.CouponIssueLogDTO;
import io.saim.dash.coupon.common.dto.Coupon.RegisteredCouponDTO;
import io.saim.dash.coupon.common.dto.Issue.PauseCouponsResultDTO;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.manage.dto.CancelIssueRequestDTO;
import io.saim.dash.coupon.manage.dto.CancelIssueResponseDTO;
import io.saim.dash.coupon.manage.dto.CancelRegistrationResponseDTO;
import io.saim.dash.coupon.manage.dto.ExportResponseDTO;
import io.saim.dash.coupon.manage.dto.IssuedRequestResponseDTO;
import io.saim.dash.coupon.manage.dto.UpdateUsableStatusRequestDTO;
import io.saim.dash.coupon.manage.dto.PauseCouponsResponseDTO;
import io.saim.dash.coupon.manage.service.ManageService;
import io.saim.dash.global.dto.PagingResponse;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coupon/manage")
@RequiredArgsConstructor
public class ManageController {

	private final ManageService manageService;
	private final PhoneVerificationService phoneVerificationService;

	@GetMapping("/list")
	public PagingResponse<IssuedRequestResponseDTO> getApprovedIssues(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestParam(required = false, defaultValue = "1") Integer page,
		@RequestParam(required = false, defaultValue = "10") Integer size,
		@RequestParam(required = false) String vendor_name,
		@RequestParam(required = false) String president_name,
		@RequestParam(required = false) String business_name,
		@RequestParam(required = false, defaultValue = "false") Boolean include_completed
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);

		List<CouponIssueLogDTO> savedIssuedRequest = manageService.getIssuedRequests(
			loginUser,
			page, size,
			vendor_name, president_name, business_name, include_completed
		);

		List<IssuedRequestResponseDTO> issuedRequestList = savedIssuedRequest.stream()
			.map(IssuedRequestResponseDTO::new)
			.toList();

		return new PagingResponse<>(
			page, size,
			issuedRequestList
		);
	}

	@GetMapping("/{issue_id}/list")
	public PagingResponse<CouponBriefDTO> getIssuedCoupons(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long issue_id,
		@RequestParam(required = false, defaultValue = "1") Integer page,
		@RequestParam(required = false, defaultValue = "10") Integer size
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);

		List<CouponBriefDTO> coupons = manageService.getCouponsByIssueId(
			loginUser,
			page, size,
			issue_id
		);

		return new PagingResponse<>(
			page, size,
			coupons
		);
	}

	// @GetMapping("/{issue_id}/{coupon_id}")
	@GetMapping("/coupon/{coupon_id}")
	public RegisteredCouponDTO getCouponSpec(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long coupon_id
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);

		RegisteredCouponDTO specCoupon = manageService.getCouponByCouponId(
			loginUser,
			coupon_id
		);

		return specCoupon;
	}

	@PatchMapping("/{issue_id}/status")
	public PauseCouponsResponseDTO updateCouponUsableStatus(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long issue_id,
		@RequestBody UpdateUsableStatusRequestDTO updateRequestDTO
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);

		PauseCouponsResultDTO usableUpdateResult = manageService.updateCouponsPauseStatus(
			loginUser,
			issue_id, updateRequestDTO.getStatus()
		);
		return new PauseCouponsResponseDTO(issue_id, updateRequestDTO.getStatus(), usableUpdateResult);
	}

	@PostMapping("/{issue_id}/{coupon_id}/cancel")
	public CancelRegistrationResponseDTO cancelCouponRegistration(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long issue_id,
		@PathVariable Long coupon_id
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);

		CouponRegistration cancelResult = manageService.cancelCouponRegistration(loginUser, issue_id, coupon_id);
		return new CancelRegistrationResponseDTO(cancelResult);
	}

	@PostMapping("/{issue_id}/cancel/request")
	public void requestIssueCancellation(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long issue_id
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);

		Issue requestedCancelIssue = manageService.getRequestedCancelIssue(loginUser, issue_id);

		// 파트너 인증문자 발송
		PartnerUser partnerUser = requestedCancelIssue.getPartner();
		phoneVerificationService.requestVerification(partnerUser.getPhone());
	}

	@PostMapping("/{issue_id}/cancel")
	public CancelIssueResponseDTO cancelIssue(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long issue_id,
		@RequestBody CancelIssueRequestDTO cancelIssueRequestDTO
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);

		Issue issue = manageService.getRequestedCancelIssue(loginUser, issue_id);
		Boolean verifyResult = phoneVerificationService.verifyCode(
			issue.getPartner().getPhone(),
			cancelIssueRequestDTO.getUserVerifyCode()
		);

		CancelIssueResultDTO cancelResult = manageService.cancelIssue(loginUser, issue, verifyResult);

		return new CancelIssueResponseDTO(cancelResult);
	}

	@GetMapping("/{issue_id}/export/list")
	public ExportResponseDTO exportCouponList(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable(name = "issue_id") Long issueId
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);
		return manageService.exportCouponList(loginUser, CouponExportType.csv, issueId);
	}


	@GetMapping("/{issue_id}/export/image")
	public ExportResponseDTO exportCouponImage(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable(name = "issue_id") Long issueId
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);
		return manageService.exportCouponList(loginUser, CouponExportType.image, issueId);
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
