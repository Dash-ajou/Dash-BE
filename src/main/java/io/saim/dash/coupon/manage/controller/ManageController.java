package io.saim.dash.coupon.manage.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.coupon.common.dto.CouponDTO;
import io.saim.dash.coupon.common.dto.CouponIssueLogDTO;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.manage.dto.IssuedIRResponseDTO;
import io.saim.dash.coupon.manage.service.ManageService;
import io.saim.dash.global.dto.PagingResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coupon/manage")
@RequiredArgsConstructor
public class ManageController {

	private final ManageService manageService;

	@GetMapping("/list")
	public PagingResponse<IssuedIRResponseDTO> getIssuedIRList(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@RequestParam(required = false, defaultValue = "1") Integer page,
		@RequestParam(required = false, defaultValue = "10") Integer size,
		@RequestParam(required = false) String vendor_name,
		@RequestParam(required = false) String president_name,
		@RequestParam(required = false) String business_name,
		@RequestParam(required = false, defaultValue = "false") Boolean include_completed
	) {
		List<CouponIssueLogDTO> savedIssuedIRList = manageService.getIssuedIRs(
			serviceUser,
			page, size,
			vendor_name, president_name, business_name, include_completed
		);

		List<IssuedIRResponseDTO> issuedIRList = savedIssuedIRList.stream()
			.map(IssuedIRResponseDTO::new)
			.toList();

		return new PagingResponse<>(
			page, size,
			issuedIRList
		);
	}

	@GetMapping("/{issue_id}/list")
	public PagingResponse<CouponDTO> getIssuedCouponList(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@PathVariable Integer page,
		@PathVariable Integer size,
		@PathVariable Long issue_id
	) {
		List<CouponDTO> coupons = manageService.getCouponsByIssueId(
			serviceUser,
			page, size,
			issue_id
		);

		return new PagingResponse<>(
			page, size,
			coupons
		);
	}
}
