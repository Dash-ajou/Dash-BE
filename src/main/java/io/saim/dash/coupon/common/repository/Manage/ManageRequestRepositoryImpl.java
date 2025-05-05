package io.saim.dash.coupon.common.repository.Manage;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.common.dto.CouponIssueLogDTO;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.QIssueLog;
import io.saim.dash.coupon.common.model.QIssueRequest;
import io.saim.dash.coupon.common.repository.jpa.IssueLogJpaRepository;
import io.saim.dash.coupon.common.repository.jpa.IssueRequestJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ManageRequestRepositoryImpl implements ManageRequestRepository {

	private final JPAQueryFactory queryFactory;
	private final IssueLogJpaRepository issueLogJpaRepository;
	private final IssueRequestJpaRepository issueRequestJpaRepository;

	@Override
	public List<CouponIssueLogDTO> findIRsByPartner(DUMMY_PartnerUser user, BooleanBuilder filter, Integer page, Integer size) {
		QIssueRequest issueRequest = QIssueRequest.issueRequest;
		QIssueLog issueLog = QIssueLog.issueLog;

		filter.and(issueRequest.partner.eq(user));
		List<Tuple> savedIssuedRequests = getIssuedRequests(filter, issueRequest, issueLog);

		return savedIssuedRequests.stream()
			.map(CouponIssueLogDTO::new)
			.toList();
	}

	@Override
	public List<CouponIssueLogDTO> findIRsByVendor(DUMMY_GeneralUser user, BooleanBuilder filter, Integer page, Integer size) {
		QIssueRequest issueRequest = QIssueRequest.issueRequest;
		QIssueLog issueLog = QIssueLog.issueLog;

		filter.and(issueRequest.vendorGroup.in(user.getVendors()));
		List<Tuple> savedIssuedRequests = getIssuedRequests(filter, issueRequest, issueLog);

		return savedIssuedRequests.stream()
			.map(CouponIssueLogDTO::new)
			.toList();
	}

	private List<Tuple> getIssuedRequests(BooleanBuilder filter, QIssueRequest issueRequest, QIssueLog issueLog) {
		return queryFactory
			.select(
				issueRequest.requestId, issueRequest.vendorGroup, issueRequest.partner,
				issueLog.issueId, issueLog.couponActiveStatus, issueLog.decidedAt, issueLog.issueCnt, issueLog.usedCnt
			)
			.from(issueLog)
			.where(filter)
			.orderBy(issueLog.decidedAt.desc())
			.fetch();
	}
}
