package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.partner.dto.CouponStatsDTO;
import io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO;
import io.saim.dash.account.partner.dto.RequestDetailDTO;
import io.saim.dash.coupon.common.model.Coupon;

public interface CouponRepository {
	List<Coupon> findCouponsByIssueId(Long issueId);

	Coupon findCouponById(Long couponId);

	Long cancelCoupons(BooleanBuilder couponFilterBuilder);

	boolean existsById(Long couponId);

	void deleteById(Long couponId);

	Optional<Coupon> findById(Long couponId);

	List<Coupon> findByGeneralUserId(Long generalUserId);

	Coupon findByRegistrationCode(String couponRegistrationCode);

	Optional<CouponStatsDTO> getOverallStatsByPartnerId(Long partnerId);

	List<CouponVendorDetailStatsDTO> getVendorStatsByPartnerId(Long partnerId);

	List<CouponVendorDetailStatsDTO> getDetailedVendorStatsByPartnerId(Long partnerId);

	List<RequestDetailDTO> getRequestDetailsByVendorId(Long vendorId);
}
