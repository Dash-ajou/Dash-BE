package io.saim.dash.account.partner.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.saim.dash.coupon.common.model.Vendor;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.account.partner.dto.CouponStatsDTO;
import io.saim.dash.account.partner.dto.CouponStatsResponseDTO;
import io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO;
import io.saim.dash.account.partner.dto.RequestDetailDTO;
import io.saim.dash.account.partner.dto.VendorDetailInfoDTO;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.coupon.common.repository.Vendor.VendorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponStatsService {

	private final CouponRepository couponRepository;
	private final VendorRepository vendorRepository;

	public CouponStatsResponseDTO getPartnerCouponStats(Long partnerId) {
		CouponStatsDTO overall = couponRepository.getOverallStatsByPartnerId(partnerId)
			.orElse(new CouponStatsDTO(0L, 0L, 0L));

		List<CouponVendorDetailStatsDTO> detailed = couponRepository.getVendorStatsByPartnerId(partnerId);

		return CouponStatsResponseDTO.builder()
			.totalIssued(overall.getTotalIssued())
			.totalUsed(overall.getTotalUsed())
			.totalRemainder(overall.getTotalRemainder())
			.usageRate(overall.getUsageRate())
			.detailedStats(detailed)
			.build();
	}

	public List<CouponVendorDetailStatsDTO> getDetailedStats(Long partnerId) {
		return couponRepository.getDetailedVendorStatsByPartnerId(partnerId);
	}

	// CouponStatsService.java 안에서
	public VendorDetailInfoDTO getVendorDetailInfo(Long vendorId) {
		Vendor vendor = vendorRepository.findById(vendorId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 발급 단체를 찾을 수 없습니다."));

		List<RequestDetailDTO> details = couponRepository.getRequestDetailsByVendorId(vendorId);

		return VendorDetailInfoDTO.builder()
			.vendorName(vendor.getName())
			.headName(vendor.getPresidentName())
			.headContact(vendor.getPresidentPhone())
			.details(details)
			.build();
	}
}
