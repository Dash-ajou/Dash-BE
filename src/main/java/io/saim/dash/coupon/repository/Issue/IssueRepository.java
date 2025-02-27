package io.saim.dash.coupon.repository.Issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.saim.dash.coupon.common.dto.IssueFilterDTO;
import io.saim.dash.coupon.model.Issue;

public interface IssueRepository {
	Page<Issue> findIssueRequest(IssueFilterDTO filter, Pageable pageable);
}
