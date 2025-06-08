package io.saim.dash.account.partner.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.saim.dash.account.partner.dto.PartnerAutocompleteDTO;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/partner/search")
@RequiredArgsConstructor
public class PartnerSearchController {

	private final PartnerUserRepository partnerUserRepository;

	@GetMapping("/autocomplete")
	public ResponseEntity<CommonResponseDTO<List<PartnerAutocompleteDTO>>> autocompletePartner(
		@RequestParam("keyword") String keyword,
		@CookieValue(value = "SESSION", required = true) String cookie
	) {
		List<PartnerAutocompleteDTO> result =
			partnerUserRepository.findTop10ByPartnerNameContainingOrPhoneContaining(keyword, keyword)
				.stream()
				.map(PartnerAutocompleteDTO::new)
				.collect(Collectors.toList());

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"파트너 자동완성 검색 성공",
				result
			)
		);
	}
}
