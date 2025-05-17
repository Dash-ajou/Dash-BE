package io.saim.dash.account.general.coupon.repository;

import io.saim.dash.account.general.coupon.model.Coupon;
import io.saim.dash.account.partner.dto.CouponStatsDTO;
import io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO;
import io.saim.dash.account.partner.dto.RequestDetailDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	List<Coupon> findByGeneralUser_Id(Long generalUserId);
	Optional<Coupon> findByCouponNumber(String couponNumber);

	@Query("""
	SELECT new io.saim.dash.account.partner.dto.CouponStatsDTO(
		COUNT(c),
		SUM(CASE WHEN c.couponStatus = io.saim.dash.account.general.coupon.model.Coupon.CouponStatus.USED THEN 1 ELSE 0 END),
		SUM(CASE WHEN c.couponStatus = io.saim.dash.account.general.coupon.model.Coupon.CouponStatus.ACTIVE THEN 1 ELSE 0 END)
	)
	FROM Coupon c
	WHERE c.product.partner.id = :partnerId
""")
	Optional<CouponStatsDTO> getOverallStatsByPartnerId(@Param("partnerId") Long partnerId);

	@Query("""
	SELECT new io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO(
		p.productId,
		p.productName,
		COUNT(c),
		SUM(CASE WHEN c.couponStatus = io.saim.dash.account.general.coupon.model.Coupon.CouponStatus.USED THEN 1 ELSE 0 END)
	)
	FROM Coupon c
	JOIN c.product p
	WHERE p.partner.id = :partnerId
	GROUP BY p.productId, p.productName
""")
	List<CouponVendorDetailStatsDTO> getVendorStatsByPartnerId(@Param("partnerId") Long partnerId);

	@Query("""
	SELECT new io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO(
		p.productId,
		p.productName,
		COUNT(c),
		SUM(CASE WHEN c.couponStatus = io.saim.dash.account.general.coupon.model.Coupon.CouponStatus.USED THEN 1 ELSE 0 END)
	)
	FROM Coupon c
	JOIN c.product p
	WHERE p.partner.id = :partnerId
	GROUP BY p.productId, p.productName
""")
	List<CouponVendorDetailStatsDTO> getDetailedVendorStatsByPartnerId(@Param("partnerId") Long partnerId);

	@Query("""
SELECT new io.saim.dash.account.partner.dto.RequestDetailDTO(
    cm.requestDetail,
    cm.requestCount,
    cm.totalPrice,
    cm.approvalDate
)
FROM CouponRequest cm
JOIN cm.product p
WHERE p.partner.id = :vendorId
""")
	List<RequestDetailDTO> getRequestDetailsByVendorId(Long vendorId);
}
