package io.saim.dash.account.auth.controller;

import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogoutController {

	@PostMapping("/logout")
	public ResponseEntity<CommonResponseDTO<Void>> logout(HttpSession session) {
		SecurityContextHolder.clearContext();
		session.invalidate();

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"로그아웃 되었습니다.",
				null
			)
		);
	}
}
