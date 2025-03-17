package io.saim.dash.coupon.issue.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.saim.dash.coupon.model.DUMMY_ServiceUser;
import lombok.RequiredArgsConstructor;

import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.issue.service.IssueService;

@RestController
@RequestMapping("/coupon/issue")
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@GetMapping("/spec/{issueId}")
	public Issue getIssueRequestSpec(
		@PathVariable Long issueId,
		@AuthenticationPrincipal DUMMY_ServiceUser user
	) {
		return issueService.getIssue(issueId, user);
	}
}
