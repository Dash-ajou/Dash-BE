package io.saim.dash.account.general.coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.CouponPaymentLog;

public interface CouponPaymentLogRepository extends JpaRepository<CouponPaymentLog, Long> {
	List<CouponPaymentLog> findAllByUser(GeneralUser user);

	@Query("SELECT log FROM CouponPaymentLog log " +
		"JOIN log.paymentCode pc " +
		"JOIN pc.coupon c " +
		"JOIN c.issue i " +
		"JOIN i.partner p " +
		"WHERE c.couponStatus = :status AND p.id = :partnerId")
	List<CouponPaymentLog> findUsedLogsByPartnerId(@Param("partnerId") Long partnerId,
		@Param("status") CouponStatus status);
}
