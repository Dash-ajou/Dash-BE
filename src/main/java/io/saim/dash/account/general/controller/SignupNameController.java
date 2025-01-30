package io.saim.dash.account.general.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.saim.dash.account.general.dto.SignupNameRequestDTO;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.service.SignupNameService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupNameController {

	private final SignupNameService signupNameService;

	@PostMapping("/name")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupNameRequestDTO requestDTO) {
		SignupName user = signupNameService.registerUser(requestDTO.getGeneralName());

		return ResponseEntity.status(201).body(Map.of(
			"status", "SUCCESS",
			"message", "이름이 저장되었습니다.",
			"data", Map.of("general_id", user.getGeneralId())
		));
	}
}
