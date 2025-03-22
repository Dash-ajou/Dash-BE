package io.saim.dash.coupon.repository.Issue;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.issue.dto.IssueFilter;
import io.saim.dash.coupon.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.model.QIssue;
import io.saim.dash.coupon.model.VendorGroup;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class IssueRepositoryImpl implements IssueRepository {

	private final JPAQueryFactory queryFactory;
	private IssueJpaRepository jpaRepository;

	@Override
	public Optional<Issue> getById(long issueId) {
		return jpaRepository.findById(issueId);
	}

	@Override
	public List<Issue> findIssuesByVendor(DUMMY_GeneralUser user, IssueFilter filter) {
		QIssue issue = QIssue.issue;
		BooleanBuilder vendorOption = new BooleanBuilder();

		List<VendorGroup> vendors = user.getVendors();
		if (!vendors.isEmpty()) {
			vendorOption.and(issue.vendorGroup.in(vendors));
		}

		return queryFactory
			// Search Filtering
			.selectFrom(issue)
			.where(vendorOption)
			.orderBy(issue.createdAt.desc()) // 최신요청 순

			// Pagination
			.offset(filter.getOffset())
			.limit(filter.getLimit())
			.fetch();
	}

	@Override
	public List<Issue> findIssuesByPartner(DUMMY_PartnerUser user, IssueFilter filter) {
		QIssue issue = QIssue.issue;
		return queryFactory
			.selectFrom(issue)
			.where(issue.partner.eq(user))

			// Pagination
			.offset(filter.getOffset())
			.limit(filter.getLimit())
			.fetch();
	}
}
