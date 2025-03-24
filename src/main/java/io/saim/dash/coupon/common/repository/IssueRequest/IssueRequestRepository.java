package io.saim.dash.coupon.common.repository.IssueRequest;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.IssueRequest;

public interface IssueRequestRepository {

	Optional<IssueRequest> getById(long issueId);

	// List<Issue> getIssuesByVendor(DUMMY_GeneralUser user);
	List<IssueRequest> findIssuesByVendor(DUMMY_GeneralUser user, BooleanBuilder filterBuilder, int page, int size);
	// List<Issue> getIssuesByPartner(DUMMY_PartnerUser user);
	List<IssueRequest> findIssuesByPartner(DUMMY_PartnerUser user, BooleanBuilder filterBuilder, int page, int size);

	void save(IssueRequest issueRequest);

	void delete(IssueRequest issueRequest);

	// List<Issue> getIssueByUserId(Long userId);
}
