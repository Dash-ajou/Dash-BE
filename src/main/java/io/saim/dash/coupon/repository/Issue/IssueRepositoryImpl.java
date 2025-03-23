package io.saim.dash.coupon.repository.Issue;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

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
	private final IssueJpaRepository issueJpaRepository;

	@Override
	public Optional<Issue> getById(long issueId) {
		return issueJpaRepository.findById(issueId);
	}

	@Override
	public List<Issue> findIssuesByVendor(DUMMY_GeneralUser user, BooleanBuilder filterBuilder, int page, int size) {
		QIssue issue = QIssue.issue;

		List<VendorGroup> vendors = user.getVendors();
		if (!vendors.isEmpty()) {
			throw new NullPointerException("No Vendor Found");
		}

		filterBuilder.and(issue.vendorGroup.in(vendors));

		JPAQuery<Issue> issueJPAQuery = getIssueJPAQuery(filterBuilder, issue);
		addPaginateOptions(issueJPAQuery, page, size);
		return issueJPAQuery.fetch();
	}

	@Override
	public List<Issue> findIssuesByPartner(DUMMY_PartnerUser user, BooleanBuilder filterBuilder, int page, int size) {
		QIssue issue = QIssue.issue;

		filterBuilder.and(issue.partner.eq(user));

		JPAQuery<Issue> issueJPAQuery = getIssueJPAQuery(filterBuilder, issue);
		addPaginateOptions(issueJPAQuery, page, size);
		return issueJPAQuery.fetch();
	}

	@Override
	public void save(Issue issue) {
		issueJpaRepository.save(issue);
	}

	@Override
	public void delete(Issue issue) {
		issueJpaRepository.delete(issue);
	}

	private JPAQuery<Issue> getIssueJPAQuery(BooleanBuilder filterBuilder, QIssue issue) {
		return queryFactory
			.selectFrom(issue)
			.where(filterBuilder)
			.orderBy(issue.createdAt.desc()); // 최신요청 순
	}

	private static void addPaginateOptions(JPAQuery<Issue> issueJPAQuery, int page, int size) {

		if (page <= 0) page = 1;
		if (size <= 10) size = 10;
		else if (size % 10 != 0) size = (size/10)*10;

		issueJPAQuery
			// Pagination
			.offset(page * size)
			.limit(size);
	}
}
