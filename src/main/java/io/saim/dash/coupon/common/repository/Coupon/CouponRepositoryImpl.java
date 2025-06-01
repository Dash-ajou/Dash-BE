package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.account.general.model.QGeneralUser;
import io.saim.dash.account.partner.dto.CouponStatsDTO;
import io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO;
import io.saim.dash.account.partner.dto.RequestDetailDTO;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.QCoupon;
import io.saim.dash.coupon.common.model.QCouponRegistration;
import io.saim.dash.coupon.common.model.QIssue;
import io.saim.dash.coupon.common.model.QProduct;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.model.QUserVendor;
import io.saim.dash.coupon.common.model.QVendor;
import io.saim.dash.coupon.common.repository.jpa.CouponJpaRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

	private final JPAQueryFactory queryFactory;
	private final CouponJpaRepository couponJpaRepository;

	@Override
	public List<Coupon> findCouponsByIssueId(Long issueId) {
		return couponJpaRepository.findByIssueIssueId(issueId);
	}

	@Override
	public Coupon findCouponById(Long couponId) {
		return couponJpaRepository.findById(couponId).orElse(null);
	}

	@Override
	public Long cancelCoupons(BooleanBuilder couponFilterBuilder) {
		QCoupon coupon = QCoupon.coupon;
		return queryFactory
			.update(coupon)
			.set(coupon.couponStatus, CouponStatus.CANCELED)
			.where(couponFilterBuilder)
			.execute();
	}

	@Override
	public boolean existsById(Long couponId) {
		return couponJpaRepository.existsById(couponId);
	}

	@Override
	public void deleteById(Long couponId) {
		couponJpaRepository.deleteById(couponId);
	}

	@Override
	public Optional<Coupon> findById(Long couponId) {
		return couponJpaRepository.findById(couponId);
	}

	@Override
	public List<Coupon> findByGeneralUserId(Long generalUserId) {
		QCoupon c = QCoupon.coupon;
		QCouponRegistration cr = QCouponRegistration.couponRegistration;

		return queryFactory
			.select(c)
			.from(c)
			.join(cr).on(cr.coupon.eq(c))
			.where(cr.registeredUser.id.eq(generalUserId))
			.fetch();
	}

	public Coupon findByRegistrationCode(String couponRegistrationCode) {
		return couponJpaRepository.findByRegistrationCode(couponRegistrationCode)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND));
	}

	@Override
	public Optional<CouponStatsDTO> getOverallStatsByPartnerId(Long partnerId) {
		QCoupon c = QCoupon.coupon;

		Tuple result = queryFactory
			.select(
				c.count(),
				c.couponStatus.when(CouponStatus.USED).then(1).otherwise(0).sum(),
				c.couponStatus.when(CouponStatus.USABLE).then(1).otherwise(0).sum()
			)
			.from(c)
			.where(c.product.partner.id.eq(partnerId))
			.fetchOne();

		if (result == null) {
			return Optional.empty();
		}

		long total = Optional.ofNullable(result.get(0, Number.class)).map(Number::longValue).orElse(0L);
		long used = Optional.ofNullable(result.get(1, Number.class)).map(Number::longValue).orElse(0L);
		long usable = Optional.ofNullable(result.get(2, Number.class)).map(Number::longValue).orElse(0L);

		CouponStatsDTO dto = new CouponStatsDTO(total, used, usable);

		return Optional.of(dto);
	}

	@Override
	public List<CouponVendorDetailStatsDTO> getVendorStatsByPartnerId(Long partnerId) {
		QCoupon c = QCoupon.coupon;
		QProduct p = QProduct.product;

		return queryFactory
			.select(Projections.constructor(
				CouponVendorDetailStatsDTO.class,
				p.productId,
				p.productName,
				c.count(),
				c.couponStatus.when(CouponStatus.USED).then(1).otherwise(0).sum()
			))
			.from(c)
			.join(c.product, p)
			.where(p.partner.id.eq(partnerId))
			.groupBy(p.productId, p.productName)
			.fetch();
	}

	@Override
	public List<CouponVendorDetailStatsDTO> getDetailedVendorStatsByPartnerId(Long partnerId) {
		QCoupon c = QCoupon.coupon;
		QProduct p = QProduct.product;

		return queryFactory
			.select(Projections.constructor(
				CouponVendorDetailStatsDTO.class,
				p.productId,
				p.productName,
				c.count(),
				c.couponStatus.when(CouponStatus.USED).then(1).otherwise(0).sum()
			))
			.from(c)
			.join(c.product, p)
			.where(p.partner.id.eq(partnerId))
			.groupBy(p.productId, p.productName)
			.fetch();
	}

	@Override
	public List<RequestDetailDTO> getRequestDetailsByVendorId(Long vendorId) {
		return List.of();
		// TODO: 설계 오류로 인한 임시 주석처리
		// return couponJpaRepository.getRequestDetailsByVendorId(vendorId);
	}

}
