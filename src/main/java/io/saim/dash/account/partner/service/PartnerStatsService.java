package io.saim.dash.account.partner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.saim.dash.account.partner.dto.MenuVendorStatsResponseDTO;
import io.saim.dash.account.partner.dto.MenuVendorStatsResultDTO;

import io.saim.dash.account.partner.dto.VendorRawStatsDTO;
import io.saim.dash.account.partner.repository.CouponStatsJpaRepository;
import io.saim.dash.coupon.common.constant.CouponStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartnerStatsService {

	private final CouponStatsJpaRepository couponStatsJpaRepository;

	public MenuVendorStatsResultDTO getMenuVendorStats(String menuName) {
		List<VendorRawStatsDTO> rawStats =
			couponStatsJpaRepository.findStatsByMenuNameAndStatus(menuName, CouponStatus.USED);

		List<MenuVendorStatsResponseDTO> vendorStats = rawStats.stream()
			.map(row -> {
				int issued = row.issuedCount().intValue();
				int used = row.usedCount().intValue();
				int remained = issued - used;
				String usableStatus = remained > 0 ? "사용 가능" : "잔여 없음";

				return MenuVendorStatsResponseDTO.builder()
					.vendor_name(row.vendorName())
					.vendor_issued(issued)
					.vendor_used(used)
					.vendor_remained(remained)
					.usable_status(usableStatus)
					.build();
			})
			.toList();

		return MenuVendorStatsResultDTO.builder()
			.menu_name(menuName)
			.menu_vendors(vendorStats.size())
			.vendors(vendorStats)
			.build();
	}
}
