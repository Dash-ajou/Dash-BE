package io.saim.dash.account.general.coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.coupon.common.model.CouponPaymentLog;

public interface CouponPaymentLogRepository extends JpaRepository<CouponPaymentLog, Long> {
	List<CouponPaymentLog> findAllByUser(GeneralUser user);
}
