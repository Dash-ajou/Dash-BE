package io.saim.dash.account.partner.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.partner.dto.PartnerAutocompleteDTO;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/partner/search")
@RequiredArgsConstructor
public class PartnerSearchController {

	private final PartnerUserRepository partnerUserRepository;

	@GetMapping("/autocomplete")
	public ResponseEntity<?> autocompletePartner(
		@RequestParam("keyword") String keyword,
		@CookieValue(value = "SESSION", required = true) String cookie
	) {
		List<PartnerAutocompleteDTO> result = partnerUserRepository.findByPartnerNameContaining(keyword)
			.stream()
			.map(PartnerAutocompleteDTO::new)
			.collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("status", "SUCCESS");
		response.put("message", "파트너 자동완성 검색 성공");
		response.put("data", result);

		return ResponseEntity.ok(response);
	}
}
