package io.saim.dash.coupon.common.repository.Issue;

import java.util.List;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.model.Issue;

public interface IssueRepository {

	List<Issue> findIssuesByPartner(PartnerUser user, BooleanBuilder filterBuilder, Integer page, Integer size);

	List<Issue> findIssuesByVendor(GeneralUser user, BooleanBuilder filterBuilder, Integer page, Integer size);

	Issue getById(Long id);

	void save(Issue issue);

	Issue getReferenceById(Long issueId);
}

