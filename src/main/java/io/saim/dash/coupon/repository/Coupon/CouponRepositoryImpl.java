package io.saim.dash.coupon.repository.Coupon;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.model.Coupon;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CouponRepositoryImpl implements CouponRepository {

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
}

