package io.saim.dash.account.partner.controller;

import java.util.HashMap;
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

			Map<String, Object> response = new HashMap<>();
			response.put("apiVersion", "1.0");
			response.put("clientVersion", "1.0");
			response.put("status", "SUCCESS");
			response.put("message", "검색 결과입니다.");
			response.put("data", result);

			return ResponseEntity.ok(response);

		} catch (ServiceException e) {
			Map<String, Object> error = new HashMap<>();
			error.put("apiVersion", "1.0");
			error.put("clientVersion", "1.0");
			error.put("status", "FAILED");
			error.put("message", e.getMessage());
			error.put("data", null);

			return ResponseEntity.badRequest().body(error);
		}
	}
}
