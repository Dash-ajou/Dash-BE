package io.saim.dash.account.auth.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogoutController {

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		SecurityContextHolder.clearContext();
		session.invalidate();

		return ResponseEntity.ok(Map.of(
			"status", "SUCCEED",
			"message", "로그아웃 되었습니다.",
			"data", Collections.emptyMap()
		));
	}
}
