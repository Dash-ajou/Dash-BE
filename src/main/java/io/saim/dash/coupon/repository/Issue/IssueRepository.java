package io.saim.dash.coupon.repository.Issue;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import io.saim.dash.coupon.issue.dto.IssueFilter;
import io.saim.dash.coupon.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.global.dto.PageOptions;

public interface IssueRepository {

	Optional<Issue> getById(long issueId);

	// List<Issue> getIssuesByVendor(DUMMY_GeneralUser user);
	List<Issue> findIssuesByVendor(DUMMY_GeneralUser user, IssueFilter filter);
	// List<Issue> getIssuesByPartner(DUMMY_PartnerUser user);
	List<Issue> findIssuesByPartner(DUMMY_PartnerUser user, IssueFilter filter);

	// List<Issue> getIssueByUserId(Long userId);
}
