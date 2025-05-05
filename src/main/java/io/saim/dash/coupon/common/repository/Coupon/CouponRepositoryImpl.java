package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.IssueRequest;
import io.saim.dash.coupon.common.model.QCoupon;
import io.saim.dash.coupon.common.model.QIssueLog;
import io.saim.dash.coupon.common.model.QIssueRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CouponRepositoryImpl implements CouponRepository {

	private final JPAQueryFactory queryFactory;
	private final CouponJpaRepository couponJpaRepository;

	@Override
	public void save(Coupon coupon) {
		couponJpaRepository.save(coupon);
	}

	@Override
	public Optional<Coupon> getById(Long id) {
		return couponJpaRepository.findById(id);
	}

	@Override
	public void saveAll(List<Coupon> issuedCoupons) {
		couponJpaRepository.saveAll(issuedCoupons);
	}

	@Override
	public List<Coupon> getByIssueId(Long issueId, int page, int size) {
		QCoupon coupon = QCoupon.coupon;

		JPAQuery<Coupon> couponJPAQuery = getCouponJpaQuery(
			new BooleanBuilder().and(coupon.issueLog.issueId.eq(issueId)),
			coupon
		);
		addPaginateOptions(couponJPAQuery, page, size);
		return couponJPAQuery.fetch();
	}

	@Override
	public List<Coupon> getByRegisteredUserId(Long userId, int page, int size) {
		QCoupon coupon = QCoupon.coupon;
		QIssueRequest issueRequest = QIssueRequest.issueRequest;

		JPAQuery<Coupon> couponJPAQuery = queryFactory
			.select(coupon)
			.from(coupon).leftJoin(issueRequest).fetchJoin()
			.where(
				new BooleanBuilder()
					.and(issueRequest.vendorGroup.members.any().user.id.eq(userId))
			);
		return couponJPAQuery.fetch();
	}

	private JPAQuery<Coupon> getCouponJpaQuery(BooleanBuilder filterBuilder, QCoupon coupon) {
		QIssueLog issueLog = QIssueLog.issueLog;
		return queryFactory
			.selectFrom(coupon).leftJoin(issueLog).fetchJoin()
			.where(filterBuilder)
			.orderBy(coupon.couponStatus.asc()); // 등록가능부터 먼저 보이도록 설정
	}

	private static void addPaginateOptions(JPAQuery<Coupon> couponJPAQuery, int page, int size) {

		if (page <= 0) page = 1;
		if (size <= 10) size = 10;
		else if (size % 10 != 0) size = (size/10)*10;

		couponJPAQuery
			// Pagination
			.offset(page * size)
			.limit(size);
	}
}

