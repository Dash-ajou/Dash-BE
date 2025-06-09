package io.saim.dash.account.auth.controller;

import io.saim.dash.account.auth.dto.LoginRequestDTO;
import io.saim.dash.account.auth.dto.LoginResponseDTO;
import io.saim.dash.account.auth.service.LoginService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
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
	public ResponseEntity<CommonResponseDTO<LoginResponseDTO>> login(
		@Valid @RequestBody LoginRequestDTO requestDTO,
		HttpSession session
	) {
		if (requestDTO.getUserType() != null) {
			session.setAttribute("user_type", requestDTO.getUserType());
		}

		LoginResponseDTO response = authService.login(
			requestDTO.getUserPhone(),
			requestDTO.getUserPassword(),
			session
		);

		session.setAttribute("user_id", response.getUser().getUserId());

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"로그인 성공",
				response
			)
		);
	}
}
