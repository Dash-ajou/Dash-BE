package io.saim.dash.coupon.common.repository.Issue;

import java.util.List;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.Issue;

public interface IssueRepository {

	List<Issue> findIssuesByPartner(DUMMY_PartnerUser user, BooleanBuilder filterBuilder, Integer page, Integer size);

	List<Issue> findIssuesByVendor(DUMMY_GeneralUser user, BooleanBuilder filterBuilder, Integer page, Integer size);

	Issue getById(Long id);

	void save(Issue issue);

	Issue getReferenceById(Long issueId);
}
