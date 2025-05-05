package io.saim.dash.coupon.common.repository.IssueRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.IssueRequest;
import io.saim.dash.coupon.common.model.QIssueRequest;
import io.saim.dash.coupon.common.model.VendorGroup;
import io.saim.dash.coupon.common.repository.jpa.IssueRequestJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class IssueRequestRepositoryImpl implements IssueRequestRepository {

	private final JPAQueryFactory queryFactory;
	private final IssueRequestJpaRepository issueRequestJpaRepository;

	@Override
	public Optional<IssueRequest> getById(long issueRequestId) {
		return issueRequestJpaRepository.findById(issueRequestId);
	}

	@Override
	public List<IssueRequest> findIssuesByVendor(DUMMY_GeneralUser user, BooleanBuilder filterBuilder, int page, int size) {
		QIssueRequest issue = QIssueRequest.issueRequest;

		List<VendorGroup> vendors = user.getVendors();
		if (!vendors.isEmpty()) {
			throw new NullPointerException("No Vendor Found");
		}

		filterBuilder.and(issue.vendorGroup.in(vendors));

		JPAQuery<IssueRequest> issueJPAQuery = getIssueJPAQuery(filterBuilder, issue);
		addPaginateOptions(issueJPAQuery, page, size);
		return issueJPAQuery.fetch();
	}

	@Override
	public List<IssueRequest> findIssuesByPartner(DUMMY_PartnerUser user, BooleanBuilder filterBuilder, int page, int size) {
		QIssueRequest issue = QIssueRequest.issueRequest;

		filterBuilder.and(issue.partner.eq(user));

		JPAQuery<IssueRequest> issueJPAQuery = getIssueJPAQuery(filterBuilder, issue);
		addPaginateOptions(issueJPAQuery, page, size);
		return issueJPAQuery.fetch();
	}

	@Override
	public void save(IssueRequest issueRequest) {
		issueRequestJpaRepository.save(issueRequest);
	}

	@Override
	public void delete(IssueRequest issueRequest) {
		issueRequestJpaRepository.delete(issueRequest);
	}

	private JPAQuery<IssueRequest> getIssueJPAQuery(BooleanBuilder filterBuilder, QIssueRequest issue) {
		return queryFactory
			.selectFrom(issue)
			.where(filterBuilder)
			.orderBy(issue.createdAt.desc()); // 최신요청 순
	}

	private static void addPaginateOptions(JPAQuery<IssueRequest> issueJPAQuery, int page, int size) {

		if (page <= 0) page = 1;
		if (size <= 10) size = 10;
		else if (size % 10 != 0) size = (size/10)*10;

		issueJPAQuery
			// Pagination
			.offset(page * size)
			.limit(size);
	}
}
