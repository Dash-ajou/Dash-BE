package io.saim.dash.account.partner.controller;

import io.saim.dash.account.partner.dto.PartnerAccountResponseDTO;
import io.saim.dash.account.partner.service.PartnerAccountService;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerAccountController {

	private final PartnerAccountService partnerAccountService;

	@GetMapping("/account")
	public ResponseEntity<CommonResponseDTO<PartnerAccountResponseDTO>> getPartnerAccountDetails(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null || !"PARTNER".equals(userDetails.getUserType())) {
			return ResponseEntity.status(401).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"인증되지 않은 파트너 사용자입니다.",
				null
			));
		}

		CommonResponseDTO<PartnerAccountResponseDTO> response =
			partnerAccountService.getPartnerAccountDetails(userDetails.getUsername());

		if (response.getStatus() == APIStatus.FAILURE) {
			return ResponseEntity.status(403).body(response);
		}

		return ResponseEntity.ok(response);
	}
}
