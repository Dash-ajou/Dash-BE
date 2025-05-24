package io.saim.dash.account.general.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.general.dto.SignupCompleteResponseDTO;
import io.saim.dash.account.general.dto.TestDTO;
import io.saim.dash.account.general.dto.UnifiedSignupRequestDTO;
import io.saim.dash.account.general.service.UnifiedSignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class UnifiedSignupController {

	private final UnifiedSignupService unifiedSignupService;

	@PostMapping("/unified")
	public ResponseEntity<?> unifiedSignup(
		@Valid @RequestBody UnifiedSignupRequestDTO dto) {

		SignupCompleteResponseDTO response = unifiedSignupService.signup(dto);

		return ResponseEntity.status(201).body(response.getData());
	}

	@PostMapping("/test")
	public ResponseEntity<?> test(
		@RequestBody TestDTO dto
	) {
		return ResponseEntity.status(201).body(unifiedSignupService.test(dto));
	}
}
