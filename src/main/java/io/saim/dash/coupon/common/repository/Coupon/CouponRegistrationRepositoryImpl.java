package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.account.general.model.QGeneralUser;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.model.QCoupon;
import io.saim.dash.coupon.common.model.QCouponRegistration;
import io.saim.dash.coupon.common.model.QIssue;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.model.QUserVendor;
import io.saim.dash.coupon.common.model.QVendor;
import io.saim.dash.coupon.common.repository.jpa.CouponRegistrationJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponRegistrationRepositoryImpl implements CouponRegistrationRepository {

	private final JPAQueryFactory queryFactory;
	private final CouponRegistrationJpaRepository couponRegistrationJPARepository;

	@Override
	public CouponRegistration findByCoupon(Coupon coupon) {
		QCouponRegistration couponRegistration = QCouponRegistration.couponRegistration;
		return queryFactory.selectFrom(couponRegistration)
			.where(
				couponRegistration.coupon.couponId.eq(coupon.getCouponId()),
				couponRegistration.isValid.eq(true)
			)
			.fetchOne();
	}

	@Override
	public CouponRegistration findByCouponId(Long couponId) {
		QCouponRegistration couponRegistration = QCouponRegistration.couponRegistration;
		return queryFactory.selectFrom(couponRegistration)
			.where(
				couponRegistration.coupon.couponId.eq(couponId),
				couponRegistration.isValid.eq(true)
			)
			.fetchOne();
	}

	@Override
	public List<CouponRegistration> findByMemberId(Long memberId) {
		QCouponRegistration couponRegistration = QCouponRegistration.couponRegistration;
		QVendor vendor = QVendor.vendor;
		QUserVendor userVendor = QUserVendor.userVendor;
		QGeneralUser generalUser = QGeneralUser.generalUser;
		return queryFactory.selectFrom(couponRegistration)
			.join(couponRegistration.coupon.issue.request.vendor, vendor)
			.join(vendor.vendorUsers, userVendor)
			.join(userVendor.user, generalUser)
			.where(generalUser.id.eq(memberId))
			.fetch();
	}

	@Override
	public Optional<CouponRegistration> findByCouponIdAndMemberId(Long couponId, Long memberId) {
		QCouponRegistration couponRegistration = QCouponRegistration.couponRegistration;
		QVendor vendor = QVendor.vendor;
		QUserVendor userVendor = QUserVendor.userVendor;
		QGeneralUser generalUser = QGeneralUser.generalUser;
		CouponRegistration searchResult = queryFactory.selectFrom(couponRegistration)
			.join(couponRegistration.coupon.issue.request.vendor, vendor)
			.join(vendor.vendorUsers, userVendor)
			.join(userVendor.user, generalUser)
			.where(
				couponRegistration.coupon.couponId.eq(couponId),
				generalUser.id.eq(memberId)
			)
			.fetchOne();

		return Optional.ofNullable(searchResult);
	}

	@Override
	public List<CouponRegistration> findByRegisteredUserIdAndIsValid(Long userId, Boolean isValid) {
		QCouponRegistration couponRegistration = QCouponRegistration.couponRegistration;
		QGeneralUser generalUser = QGeneralUser.generalUser;

		return queryFactory.selectFrom(couponRegistration)
			.join(couponRegistration.registeredUser, generalUser)
			.where(
				generalUser.id.eq(userId),
				couponRegistration.isValid.eq(isValid)
			)
			.fetch();
	}

	@Override
	public int countByRegisteredUserIdAndCoupon_CouponStatus(Long userId, CouponStatus status) {
		QCouponRegistration couponRegistration = QCouponRegistration.couponRegistration;
		QGeneralUser generalUser = QGeneralUser.generalUser;

		Long count = queryFactory
			.select(couponRegistration.count())
			.from(couponRegistration)
			.join(couponRegistration.registeredUser, generalUser)
			.where(
				generalUser.id.eq(userId),
				couponRegistration.coupon.couponStatus.eq(status)
			)
			.fetchOne();

		return count != null ? count.intValue() : 0;
	}

	@Override
	public boolean existsByCoupon(Coupon coupon) {
		return couponRegistrationJPARepository.existsByCoupon(coupon);
	}

	@Override
	public void save(CouponRegistration couponRegistration) {
		couponRegistrationJPARepository.save(couponRegistration);
	}
}
