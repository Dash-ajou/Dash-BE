package io.saim.dash.account.auth.controller;

import io.saim.dash.account.auth.dto.LoginRequestDTO;
import io.saim.dash.account.auth.dto.LoginResponseDTO;
import io.saim.dash.account.auth.service.LoginService;
import jakarta.servlet.http.HttpSession;
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
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO, HttpSession session) {
		if (requestDTO.getUserType() != null) {
			session.setAttribute("user_type", requestDTO.getUserType());
		}

		LoginResponseDTO response = authService.login(
			requestDTO.getUserPhone(),
			requestDTO.getUserPassword(),
			session
		);

		session.setAttribute("LOGIN_GENERAL_USER", response.getData().getUser());
		return ResponseEntity.ok(response);
	}
}
