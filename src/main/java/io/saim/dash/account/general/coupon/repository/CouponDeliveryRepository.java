package io.saim.dash.account.general.coupon.repository;

import java.util.List;
import java.util.Optional;

import io.saim.dash.account.general.coupon.model.CouponDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponDeliveryRepository extends JpaRepository<CouponDelivery, Long> {
	//보낸 쿠폰 목록 조회용
	List<CouponDelivery> findBySenderId(Long senderId);
	//받은 쿠폰 목록 조회용
	List<CouponDelivery> findByReceiverIdAndStatus(Long receiverId, CouponDelivery.DeliveryStatus status);

	@Query("""
		SELECT cd FROM CouponDelivery cd
		WHERE cd.coupon.id = :couponId AND cd.receiverId = :receiverId
		ORDER BY cd.requestedAt DESC
		LIMIT 1
	""")
	Optional<CouponDelivery> findLatestByCouponIdAndReceiverId(@Param("couponId") Long couponId, @Param("receiverId") Long receiverId);
}
