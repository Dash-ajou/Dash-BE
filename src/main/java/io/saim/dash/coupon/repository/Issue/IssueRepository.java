package io.saim.dash.coupon.repository.Issue;

import java.util.List;
import java.util.Optional;

import io.saim.dash.coupon.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.Issue;

public interface IssueRepository {

	Optional<Issue> getById(long issueId);

	List<Issue> getIssuesByVendor(DUMMY_GeneralUser user);
	List<Issue> getIssuesByPartner(DUMMY_PartnerUser user);

	// List<Issue> getIssueByUserId(Long userId);
}
