package io.saim.dash.account.auth.controller;

import io.saim.dash.account.auth.dto.LoginRequestDTO;
import io.saim.dash.account.auth.dto.LoginResponseDTO;
import io.saim.dash.account.auth.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
		LoginResponseDTO response = authService.login(requestDTO.getGeneralPhone(), requestDTO.getUserPassword());
		return ResponseEntity.ok(response);
	}
}
