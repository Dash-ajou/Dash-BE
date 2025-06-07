package io.saim.dash.coupon.common.repository.jpa;

import io.saim.dash.coupon.common.model.CouponPaymentCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CouponPaymentCodeJpaRepository extends JpaRepository<CouponPaymentCode, Long> {
	Optional<CouponPaymentCode> findByCoupon_CouponId(Long couponId);

	Optional<CouponPaymentCode> findByPaymentCode(String paymentCode);

	boolean existsByCoupon_CouponId(Long couponId);

	@Transactional
	@Modifying
	@Query("UPDATE CouponPaymentCode c SET c.coupon = null WHERE c.paymentCodeId = :paymentCodeId")
	void clearPaymentCodeReference(@Param("paymentCodeId") Long paymentCodeId);
}
