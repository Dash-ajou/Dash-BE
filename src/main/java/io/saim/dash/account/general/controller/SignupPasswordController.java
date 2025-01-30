package io.saim.dash.account.general.controller;

import io.saim.dash.account.general.dto.GeneralPasswordRequestDTO;
import io.saim.dash.account.general.dto.GeneralPasswordResponseDTO;
import io.saim.dash.account.general.service.SignupPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup/password")
@RequiredArgsConstructor
public class SignupPasswordController {

	private final SignupPasswordService userService;

	@PostMapping
	public ResponseEntity<GeneralPasswordResponseDTO> setPassword(@Valid @RequestBody GeneralPasswordRequestDTO requestDto) {
		GeneralPasswordResponseDTO response = userService.setPassword(requestDto);
		return ResponseEntity.status(201).body(response);
	}
}
