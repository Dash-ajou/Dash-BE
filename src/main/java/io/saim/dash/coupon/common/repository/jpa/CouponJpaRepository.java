
package io.saim.dash.coupon.common.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
	List<Coupon> findByIssueIssueId(Long issueId);

	Optional<Coupon> findByRegistrationCode(String couponRegistrationCode);

	List<Coupon> findByGeneralUser_IdAndCouponStatus(Long generalUserId, CouponStatus status);

	int countByGeneralUser_IdAndCouponStatus(Long userId, CouponStatus status);

	// TODO: 설계 오류로 인한 임시 주석처리
	// @Query("""
	// SELECT new io.saim.dash.account.partner.dto.RequestDetailDTO(
	// 	cm.requestDetail,
	// 	cm.requestCount,
	// 	cm.totalPrice,
	// 	cm.approvalDate
	// )
	// FROM Request cm
	// JOIN cm.product p
	// WHERE p.partner.id = :vendorId
	// """)
	// List<RequestDetailDTO> getRequestDetailsByVendorId(Long vendorId);
}
