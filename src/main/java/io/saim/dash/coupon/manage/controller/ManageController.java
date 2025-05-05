package io.saim.dash.coupon.manage.controller;

import java.util.List;

import javax.naming.ldap.PagedResultsControl;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.coupon.common.dto.CouponIssueLogDTO;
import io.saim.dash.coupon.common.dto.IssueResultDTO;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.common.model.IssueLog;
import io.saim.dash.coupon.manage.dto.IssuedCouponResponseDTO;
import io.saim.dash.coupon.manage.dto.MFindRequestDTO;
import io.saim.dash.coupon.manage.service.ManageService;
import io.saim.dash.global.dto.PagingResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coupon/manage")
@RequiredArgsConstructor
public class ManageController {

	private final ManageService manageService;

	@GetMapping("/list")
	public PagingResponse<IssuedCouponResponseDTO> getIssuedList(
		@AuthenticationPrincipal DUMMY_ServiceUser serviceUser,
		@RequestBody MFindRequestDTO mFindRequestDTO
	) {
		List<CouponIssueLogDTO> couponIssueResultList = manageService.getIssuedRequests(serviceUser, mFindRequestDTO);

		return new PagingResponse<>(
			mFindRequestDTO.getPage(), mFindRequestDTO.getSize(),
			couponIssueResultList.stream()
				.map(IssuedCouponResponseDTO::new)
				.toList()
		);
	}
}
