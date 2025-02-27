package io.saim.dash.coupon.repository.Issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.saim.dash.coupon.common.dto.IssueFilterDTO;
import io.saim.dash.coupon.model.Issue;

public class IssueRepositoryImpl implements IssueRepository extends  {

	@Override
	public Page<Issue> findIssueRequest(IssueFilterDTO filter, Pageable pageable) {
		return null;
	}
}
