package io.saim.dash.account.partner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.saim.dash.account.general.coupon.repository.CouponRepository;
import io.saim.dash.account.partner.dto.CouponStatsDTO;
import io.saim.dash.account.partner.dto.CouponStatsResponseDTO;
import io.saim.dash.account.partner.dto.CouponVendorDetailStatsDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponStatsService {

	private final CouponRepository couponRepository;

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
}
