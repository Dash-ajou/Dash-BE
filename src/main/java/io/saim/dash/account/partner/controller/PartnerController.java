package io.saim.dash.account.partner.controller;

import io.saim.dash.account.partner.dto.PartnerSignupRequestDTO;
import io.saim.dash.account.partner.service.PartnerService;
import io.saim.dash.global.dto.CommonResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup/partner")
@RequiredArgsConstructor
public class PartnerController {

	private final PartnerService partnerService;

	@PostMapping("/details")
	public ResponseEntity<CommonResponseDTO<?>> registerPartner(@RequestBody PartnerSignupRequestDTO requestDTO) {
		return ResponseEntity.status(201).body(partnerService.registerPartner(requestDTO));
	}
}
