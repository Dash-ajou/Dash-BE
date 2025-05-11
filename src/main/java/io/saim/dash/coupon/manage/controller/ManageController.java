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

import io.saim.dash.coupon.common.dto.Coupon.CouponBriefDTO;
import io.saim.dash.coupon.common.dto.Issue.CouponIssueLogDTO;
import io.saim.dash.coupon.common.dto.Coupon.RegisteredCouponDTO;
import io.saim.dash.coupon.common.dto.Issue.PauseCouponsResultDTO;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.manage.dto.CancelRegistrationResponseDTO;
import io.saim.dash.coupon.manage.dto.IssuedRequestResponseDTO;
import io.saim.dash.coupon.manage.dto.UpdateUsableStatusRequestDTO;
import io.saim.dash.coupon.manage.dto.PauseCouponsResponseDTO;
import io.saim.dash.coupon.manage.service.ManageService;
import io.saim.dash.global.dto.PagingResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coupon/manage")
@RequiredArgsConstructor
public class ManageController {

	private final ManageService manageService;

	@GetMapping("/list")
	public PagingResponse<IssuedRequestResponseDTO> getApprovedIssues(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@RequestParam(required = false, defaultValue = "1") Integer page,
		@RequestParam(required = false, defaultValue = "10") Integer size,
		@RequestParam(required = false) String vendor_name,
		@RequestParam(required = false) String president_name,
		@RequestParam(required = false) String business_name,
		@RequestParam(required = false, defaultValue = "false") Boolean include_completed
	) {
		List<CouponIssueLogDTO> savedIssuedRequest = manageService.getIssuedRequests(
			serviceUser,
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
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@PathVariable Long issue_id,
		@RequestParam(required = false) Integer page,
		@RequestParam(required = false) Integer size
	) {
		List<CouponBriefDTO> coupons = manageService.getCouponsByIssueId(
			serviceUser,
			page, size,
			issue_id
		);

		return new PagingResponse<>(
			page, size,
			coupons
		);
	}

	@Deprecated
	@GetMapping("/{issue_id}/{coupon_id}/register")
	public RegisteredCouponDTO deprecatedGetCouponSpec(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@PathVariable Long issue_id,
		@PathVariable Long coupon_id
	) {
		return this.getCouponSpec(serviceUser, issue_id, coupon_id);
	}

	@GetMapping("/{issue_id}/{coupon_id}")
	public RegisteredCouponDTO getCouponSpec(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@PathVariable Long issue_id,
		@PathVariable Long coupon_id
	) {

		RegisteredCouponDTO specCoupon = manageService.getCouponByCouponId(
			serviceUser,
			issue_id, coupon_id
		);

		return specCoupon;
	}

	@PatchMapping("/{issue_id}/status")
	public PauseCouponsResponseDTO updateCouponUsableStatus(
		@AuthenticationPrincipal DUMMY_ServiceUser user,
		@PathVariable Long issue_id,
		@RequestBody UpdateUsableStatusRequestDTO updateRequestDTO
	) {
		PauseCouponsResultDTO usableUpdateResult = manageService.updateCouponsPauseStatus(
			user,
			issue_id, updateRequestDTO.getStatus()
		);
		return new PauseCouponsResponseDTO(issue_id, updateRequestDTO.getStatus(), usableUpdateResult);
	}

	@PostMapping("/{issue_id}/{coupon_id}/cancel")
	public CancelRegistrationResponseDTO cancelCouponRegistration(
		@AuthenticationPrincipal DUMMY_ServiceUser user,
		@PathVariable Long issue_id,
		@PathVariable Long coupon_id
	) {
		CouponRegistration cancelResult = manageService.cancelCouponRegistration(user, issue_id, coupon_id);
		return new CancelRegistrationResponseDTO(cancelResult);
	}

	@PostMapping("/{issue_id}/cancel/request")
	public void requestIssueCancellation(
		@AuthenticationPrincipal DUMMY_ServiceUser user,
		@PathVariable Long issue_id
	) {
		manageService.checkIssueCancellable(user, issue_id);

		// 파트너 인증문자 발송

	}
}
