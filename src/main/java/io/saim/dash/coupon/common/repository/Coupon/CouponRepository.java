package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;
import java.util.Optional;

import io.saim.dash.coupon.common.model.Coupon;

public interface CouponRepository {

	void save(Coupon coupon);

	Optional<Coupon> getById(Long id);

	void saveAll(List<Coupon> issuedCoupons);

	List<Coupon> getByIssueId(Long issueId, int page, int size);

	List<Coupon> getByRegisteredUserId(Long userId, int page, int size);
}
