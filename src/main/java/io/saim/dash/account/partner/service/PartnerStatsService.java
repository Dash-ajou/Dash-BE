package io.saim.dash.account.partner.service;

import java.util.List;
import org.springframework.stereotype.Service;
import io.saim.dash.account.partner.dto.MenuVendorStatsResponseDTO;
import io.saim.dash.account.partner.dto.MenuVendorStatsResultDTO;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.account.partner.dto.VendorRawStatsDTO;
import io.saim.dash.account.partner.repository.CouponStatsJpaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartnerStatsService {

	private final CouponStatsJpaRepository couponStatsJpaRepository;

	public MenuVendorStatsResultDTO getMenuVendorStats(String menuName) {
		List<VendorRawStatsDTO> rawStats =
			couponStatsJpaRepository.findStatsGroupedByVendor(CouponStatus.USED);

		List<MenuVendorStatsResponseDTO> vendorStats = rawStats.stream()
			.map(row -> {
				int issued = row.getIssuedCount().intValue();
				int used = row.getUsedCount().intValue();
				int remained = issued - used;
				String usableStatus = remained > 0 ? "사용 가능" : "잔여 없음";

				return MenuVendorStatsResponseDTO.builder()
					.vendor_id(row.getVendorId())
					.vendor_name(row.getVendorName())
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
