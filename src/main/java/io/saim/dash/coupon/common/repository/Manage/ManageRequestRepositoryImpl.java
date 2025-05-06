package io.saim.dash.coupon.common.repository.Manage;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.common.dto.Issue.CouponIssueLogDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.common.model.QIssue;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.repository.jpa.CouponJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ManageRequestRepositoryImpl implements ManageRequestRepository {

	private final JPAQueryFactory queryFactory;
	private final CouponJpaRepository couponJpaRepository;

	@Override
	public List<CouponIssueLogDTO> findIRsByPartner(DUMMY_PartnerUser user, BooleanBuilder filter, Integer page, Integer size) {
		QRequest request = QRequest.request;
		QIssue issueLog = QIssue.issue;

		filter.and(request.partner.eq(user));
		List<Tuple> savedIssuedRequests = getIssuedRequests(filter, request, issueLog);

		return savedIssuedRequests.stream()
			.map(CouponIssueLogDTO::new)
			.toList();
	}

	@Override
	public List<CouponIssueLogDTO> findIRsByVendor(DUMMY_GeneralUser user, BooleanBuilder filter, Integer page, Integer size) {
		QRequest request = QRequest.request;
		QIssue issueLog = QIssue.issue;

		filter.and(request.vendor.in(user.getVendors()));
		List<Tuple> savedIssuedRequests = getIssuedRequests(filter, request, issueLog);

		return savedIssuedRequests.stream()
			.map(CouponIssueLogDTO::new)
			.toList();
	}

	private List<Tuple> getIssuedRequests(BooleanBuilder filter, QRequest request, QIssue issueLog) {
		return queryFactory
			.select(
				request.requestId, request.vendor, request.partner,
				issueLog.issueId, issueLog.couponActiveStatus, issueLog.decidedAt, issueLog.issueCnt, issueLog.usedCnt
			)
			.from(issueLog)
			.where(filter)
			.orderBy(issueLog.decidedAt.desc())
			.fetch();
	}

	@Override
	public List<Coupon> findCouponsByIssueId(DUMMY_ServiceUser user, Long issueId, Integer page, Integer size) {
		return couponJpaRepository.findByIssueId(issueId, PageRequest.of(page-1, size));
	}

	@Override
	public Coupon findCouponByCouponId(Long couponId) {
		return couponJpaRepository.findById(couponId).orElse(null);
	}

}
