package io.saim.dash.coupon.repository.Issue;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.Issue;

public interface IssueRepository {

	Optional<Issue> getById(long issueId);

	// List<Issue> getIssuesByVendor(DUMMY_GeneralUser user);
	List<Issue> findIssuesByVendor(DUMMY_GeneralUser user, BooleanBuilder filterBuilder, int page, int size);
	// List<Issue> getIssuesByPartner(DUMMY_PartnerUser user);
	List<Issue> findIssuesByPartner(DUMMY_PartnerUser user, BooleanBuilder filterBuilder, int page, int size);

	void save(Issue issue);

	void delete(Issue issue);

	// List<Issue> getIssueByUserId(Long userId);
}
