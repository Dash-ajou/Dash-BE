package io.saim.dash.account.general.controller;

import io.saim.dash.account.general.dto.SignupCompleteRequestDTO;
import io.saim.dash.account.general.dto.SignupCompleteResponseDTO;
import io.saim.dash.account.general.service.SignupCompleteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup/complete")
public class SignupCompleteController {

	@Autowired
	private SignupCompleteService signupCompleteService;

	@PostMapping
	public ResponseEntity<SignupCompleteResponseDTO> completeSignup(
		@Valid @RequestBody SignupCompleteRequestDTO requestDTO) {
		SignupCompleteResponseDTO response = signupCompleteService.completeSignup(requestDTO);
		return ResponseEntity.ok(response);
	}
}
