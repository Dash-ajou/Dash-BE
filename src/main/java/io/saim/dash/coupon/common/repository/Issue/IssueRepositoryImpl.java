/*package io.saim.dash.coupon.common.repository.Issue;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.dto.Issue.CouponIssueLogDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.QIssue;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.jpa.IssueJpaRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class IssueRepositoryImpl implements IssueRepository {

	private final JPAQueryFactory queryFactory;
	private final IssueJpaRepository issueJpaRepository;

	@Override
	public List<Issue> findIssuesByPartner(DUMMY_PartnerUser user, BooleanBuilder filter, Integer page, Integer size) {
		QRequest request = QRequest.request;
		filter.and(request.partner.eq(user));

		return getIssuedRequests(filter);
	}

	@Override
	public List<Issue> findIssuesByVendor(DUMMY_GeneralUser user, BooleanBuilder filter, Integer page, Integer size) {
		QRequest request = QRequest.request;
		filter.and(request.vendor.in(user.getVendors()));
		return getIssuedRequests(filter);
	}

	@Override
	public Issue getById(Long id) {
		return issueJpaRepository.findById(id)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.ISSUE_NOT_FOUND));
	}

	private List<Issue> getIssuedRequests(BooleanBuilder filter) {
		QIssue issue = QIssue.issue;

		return queryFactory
			.selectFrom(issue)
			.where(filter)
			.orderBy(issue.decidedAt.desc())
			.fetch();
	}

	@Override
	public void save(Issue issue) {
		issueJpaRepository.save(issue);
	}

	@Override
	public Issue getReferenceById(Long issueId) {
		return null;
	}
}

 */
