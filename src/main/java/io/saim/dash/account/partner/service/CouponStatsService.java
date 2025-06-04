package io.saim.dash.account.partner.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.saim.dash.account.partner.dto.MenuUsageStatsDTO;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Vendor;
import io.saim.dash.account.partner.repository.CouponStatsJpaRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.account.partner.dto.CouponStatsDTO;
import io.saim.dash.account.partner.dto.CouponStatsResponseDTO;
import io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO;
import io.saim.dash.account.partner.dto.VendorDetailInfoDTO;
import io.saim.dash.coupon.common.repository.Vendor.VendorRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponStatsService {

	private final CouponRepository couponRepository;
	private final VendorRepository vendorRepository;
	private final CouponStatsJpaRepository couponStatsJpaRepository;

	public CouponStatsResponseDTO getPartnerCouponStats(Long partnerId) {
		CouponStatsDTO overall = couponRepository.getOverallStatsByPartnerId(partnerId)
			.orElse(new CouponStatsDTO(0L, 0L, 0L));

		List<CouponVendorDetailStatsDTO> detailed = couponRepository.getVendorStatsByPartnerId(partnerId);
		List<MenuUsageStatsDTO> menuUsage = couponRepository.getMenuUsageStatsByPartnerId(partnerId);

		return CouponStatsResponseDTO.builder()
			.totalIssued(overall.getTotalIssued())
			.totalUsed(overall.getTotalUsed())
			.totalRemainder(overall.getTotalRemainder())
			.usageRate(overall.getUsageRate())
			.detailedStats(detailed)
			.menuUsage(menuUsage)
			.build();
	}

	public List<CouponVendorDetailStatsDTO> getDetailedStats(Long partnerId) {
		return couponRepository.getDetailedVendorStatsByPartnerId(partnerId);
	}

	public VendorDetailInfoDTO getVendorDetailInfo(Long vendorId) {
		Vendor vendor = vendorRepository.findById(vendorId)
			.orElseThrow(() -> new ServiceException(
				ServiceExceptionContent.VENDOR_NOT_FOUND
			));

		Long issuedCount = couponStatsJpaRepository.countByVendor(vendorId);
		Long usedCount = couponStatsJpaRepository.countUsedByVendor(vendorId, CouponStatus.USED);

		double usageRate = issuedCount == 0 ? 0.0 : (double) usedCount / issuedCount;

		return VendorDetailInfoDTO.builder()
			.vendorId(vendor.getVendorId())
			.vendorName(vendor.getName())
			.vendorIssued(issuedCount)
			.vendorUsed(usedCount)
			.vendorUsageRate(usageRate)
			.build();
	}
}
