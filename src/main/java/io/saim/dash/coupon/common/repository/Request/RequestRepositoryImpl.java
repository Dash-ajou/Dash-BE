package io.saim.dash.coupon.common.repository.Request;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.model.Vendor;
import io.saim.dash.coupon.common.repository.jpa.RequestJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RequestRepositoryImpl implements RequestRepository {

	private final JPAQueryFactory queryFactory;
	private final RequestJpaRepository requestJpaRepository;

	@Override
	public Optional<Request> getById(long issueRequestId) {
		return requestJpaRepository.findById(issueRequestId);
	}

	@Override
	public List<Request> findRequestsByVendor(DUMMY_GeneralUser user, BooleanBuilder filterBuilder, int page, int size) {
		QRequest issue = QRequest.request;

		List<Vendor> vendors = user.getVendors();
		if (!vendors.isEmpty()) {
			throw new NullPointerException("No Vendor Found");
		}

		filterBuilder.and(issue.vendor.in(vendors));

		JPAQuery<Request> issueJPAQuery = getIssueJPAQuery(filterBuilder, issue);
		addPaginateOptions(issueJPAQuery, page, size);
		return issueJPAQuery.fetch();
	}

	@Override
	public List<Request> findRequestsByPartner(DUMMY_PartnerUser user, BooleanBuilder filterBuilder, int page, int size) {
		QRequest issue = QRequest.request;

		filterBuilder.and(issue.partner.eq(user));

		JPAQuery<Request> issueJPAQuery = getIssueJPAQuery(filterBuilder, issue);
		addPaginateOptions(issueJPAQuery, page, size);
		return issueJPAQuery.fetch();
	}

	@Override
	public void save(Request request) {
		requestJpaRepository.save(request);
	}

	@Override
	public void delete(Request request) {
		requestJpaRepository.delete(request);
	}

	@Override
	public Request getReferenceById(Long requestId) {
		return requestJpaRepository.getReferenceById(requestId);
	}

	private JPAQuery<Request> getIssueJPAQuery(BooleanBuilder filterBuilder, QRequest issue) {
		return queryFactory
			.selectFrom(issue)
			.where(filterBuilder)
			.orderBy(issue.createdAt.desc()); // 최신요청 순
	}

	private static void addPaginateOptions(JPAQuery<Request> issueJPAQuery, int page, int size) {

		if (page <= 0) page = 1;
		if (size <= 10) size = 10;
		else if (size % 10 != 0) size = (size/10)*10;

		issueJPAQuery
			// Pagination
			.offset(page * size)
			.limit(size);
	}
}
