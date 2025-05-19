package io.saim.dash.account.general.coupon.repository;

import java.util.List;

import io.saim.dash.account.general.coupon.model.CouponDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponDeliveryRepository extends JpaRepository<CouponDelivery, Long> {
	//보낸 쿠폰 목록 조회용
	List<CouponDelivery> findBySenderId(Long senderId);
	//받은 쿠폰 목록 조회용
	List<CouponDelivery> findByReceiverIdAndStatus(Long receiverId, CouponDelivery.DeliveryStatus status);
}
