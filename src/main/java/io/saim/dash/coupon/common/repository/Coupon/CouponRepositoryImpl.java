package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.repository.jpa.CouponJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

	private final CouponJpaRepository couponJpaRepository;

	@Override
	public List<Coupon> findCouponsByIssueId(Long issueId) {
		return couponJpaRepository.findByIssueId(issueId);
	}

	@Override
	public Coupon findCouponById(Long couponId) {
		return couponJpaRepository.findById(couponId).orElse(null);
	}

}
