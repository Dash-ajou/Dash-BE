package io.saim.dash.account.partner.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.partner.dto.PartnerRequestResponseDTO;
import io.saim.dash.account.partner.service.PartnerRequestService;
import io.saim.dash.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/partner/requests")
@RequiredArgsConstructor
public class PartnerRequestController {

	private final PartnerRequestService service;

	@GetMapping("/search")
	public ResponseEntity<?> searchPartnerRequests(
		@RequestParam(required = false) String partner_name,
		@RequestParam(required = false) String request_status,
		@CookieValue(value = "SESSION", required = true) String cookie
	) {
		try {
			List<PartnerRequestResponseDTO> result = service.searchPartnerRequests(partner_name, request_status);
			return ResponseEntity.ok(Map.of(
				"status", "SUCCESS",
				"message", "검색 결과입니다.",
				"data", result
			));
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body(Map.of(
				"status", "FAILED",
				"message", e.getMessage(),
				"data", null
			));
		}
	}
}
