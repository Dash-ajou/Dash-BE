package io.saim.dash.account.general.controller;

import io.saim.dash.account.general.dto.GeneralAccountResponseDTO;
import io.saim.dash.account.general.service.GeneralAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/general")
@RequiredArgsConstructor
public class GeneralAccountController {

	private final GeneralAccountService generalAccountService;

	@GetMapping("/account")
	public ResponseEntity<GeneralAccountResponseDTO> getGeneralAccountDetails(@RequestHeader("Authorization") String token) {
		GeneralAccountResponseDTO response = generalAccountService.getGeneralAccountDetails(token);
		return ResponseEntity.ok(response);
	}
}
