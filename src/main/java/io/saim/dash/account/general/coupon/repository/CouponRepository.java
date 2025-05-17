package io.saim.dash.account.general.coupon.repository;

import io.saim.dash.account.general.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	List<Coupon> findByGeneralUser_Id(Long generalUserId);
	Optional<Coupon> findByCouponNumber(String couponNumber);
}
