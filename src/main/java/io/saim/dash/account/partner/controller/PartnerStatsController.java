package io.saim.dash.account.partner.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.partner.dto.MenuVendorStatsResultDTO;
import io.saim.dash.account.partner.service.PartnerStatsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/partner/stats")
@RequiredArgsConstructor
public class PartnerStatsController {

	private final PartnerStatsService statsService;

	@GetMapping("/menu/{menuName}/vendors")
	public ResponseEntity<?> getMenuVendorStats(
		@PathVariable String menuName,
		@CookieValue(value = "SESSION", required = true) String cookie
	) {
		MenuVendorStatsResultDTO result = statsService.getMenuVendorStats(menuName);

		Map<String, Object> response = new HashMap<>();
		response.put("status", "SUCCESS");
		response.put("message", "메뉴별 벤더 목록 조회 성공");
		response.put("data", result);

		return ResponseEntity.ok(response);
	}
}
