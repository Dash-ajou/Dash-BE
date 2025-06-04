package io.saim.dash.account.partner.repository;

import java.util.List;

import io.saim.dash.account.partner.dto.VendorRawStatsDTO;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponStatsJpaRepository extends JpaRepository<Coupon, Long> {

	@Query("""
        SELECT COUNT(c)
        FROM Coupon c
        JOIN c.issue i
        JOIN i.request r
        JOIN r.vendor v
        WHERE v.vendorId = :vendorId
    """)
	Long countByVendor(@Param("vendorId") Long vendorId);

	@Query("""
        SELECT COUNT(c)
        FROM Coupon c
        JOIN c.issue i
        JOIN i.request r
        JOIN r.vendor v
        WHERE v.vendorId = :vendorId AND c.couponStatus = :status
    """)
	Long countUsedByVendor(@Param("vendorId") Long vendorId, @Param("status") CouponStatus status);

	@Query("""
        SELECT new io.saim.dash.account.partner.dto.VendorRawStatsDTO(
            v.vendorId,
            v.name,
            COUNT(c),
            SUM(CASE WHEN c.couponStatus = :status THEN 1 ELSE 0 END)
        )
        FROM Coupon c
        JOIN c.issue i
        JOIN i.request r
        JOIN r.vendor v
        GROUP BY v.vendorId, v.name
    """)
	List<VendorRawStatsDTO> findStatsGroupedByVendor(@Param("status") CouponStatus status);
}
