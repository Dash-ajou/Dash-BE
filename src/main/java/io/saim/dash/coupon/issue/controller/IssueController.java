package io.saim.dash.coupon.issue.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.issue.dto.IssueResponseDTO;
import io.saim.dash.coupon.model.DUMMY_ServiceUser;
import io.saim.dash.global.dto.PagingResponse;
import lombok.RequiredArgsConstructor;

import io.saim.dash.coupon.model.Issue;
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
		@PathVariable Long issueId,
		@AuthenticationPrincipal DUMMY_ServiceUser user
	) {
		Issue issue = issueService.getIssue(issueId, user);
		return new IssueResponseDTO(issue);
	}
}
