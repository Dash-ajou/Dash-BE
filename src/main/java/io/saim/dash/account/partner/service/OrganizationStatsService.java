package io.saim.dash.account.partner.service;

import io.saim.dash.account.partner.dto.OrganizationRequestDetailDTO;
import io.saim.dash.account.partner.dto.OrganizationStatsResponseDTO;
import io.saim.dash.account.partner.repository.OrganizationStatsRepository;
import io.saim.dash.coupon.common.model.Vendor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationStatsService {

	private final OrganizationStatsRepository organizationStatsRepository;

	public OrganizationStatsResponseDTO getStats(Long vendorId) {
		Vendor vendor = organizationStatsRepository.findById(vendorId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 발급 단체입니다."));

		List<OrganizationRequestDetailDTO> details = organizationStatsRepository.findRequestDetailsByVendorId(vendorId)
			.stream().map(detail -> OrganizationRequestDetailDTO.builder()
				.request_detail(detail.getProductName())
				.request_count(detail.getRequestCount())
				.total_price(detail.getTotalPriceFormatted())
				.approval_date(detail.getApprovalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
				.build()).collect(Collectors.toList());

		return OrganizationStatsResponseDTO.builder()
			.vendor_name(vendor.getName())
			.head_name(vendor.getPresidentName())
			.head_contact(vendor.getPresidentPhone())
			.details(details)
			.build();
	}
}
