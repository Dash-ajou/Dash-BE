package io.saim.dash.coupon.common.repository.Coupon;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.partner.dto.CouponStatsDTO;
import io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO;
import io.saim.dash.account.partner.dto.MenuUsageStatsDTO;
import io.saim.dash.account.partner.dto.RequestDetailDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.CouponRegistration;

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

	Optional<Coupon> findWithProductAndPartnerById(Long couponId);

	List<MenuUsageStatsDTO> getMenuUsageStatsByPartnerId(Long partnerId);

	List<CouponRegistration> findRegistrationsByIssueIdOnCoupon(Long issueId);
}
