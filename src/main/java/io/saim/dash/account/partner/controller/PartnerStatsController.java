package io.saim.dash.account.partner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.saim.dash.account.partner.dto.MenuVendorStatsResultDTO;
import io.saim.dash.account.partner.service.PartnerStatsService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/partner/stats")
@RequiredArgsConstructor
public class PartnerStatsController {

	private final PartnerStatsService statsService;

	@GetMapping("/menu/{menuName}/vendors")
	public ResponseEntity<CommonResponseDTO<MenuVendorStatsResultDTO>> getMenuVendorStats(
		@PathVariable String menuName,
		@CookieValue(value = "SESSION", required = true) String cookie
	) {
		MenuVendorStatsResultDTO result = statsService.getMenuVendorStats(menuName);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"메뉴별 벤더 목록 조회 성공",
				result
			)
		);
	}
}
