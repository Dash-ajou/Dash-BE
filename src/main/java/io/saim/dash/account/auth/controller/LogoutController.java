package io.saim.dash.account.auth.controller;

import io.saim.dash.account.auth.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogoutController {

	private final LogoutService logoutService;

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String sessionId) {
		System.out.println("🔹 Received sessionId: " + sessionId);
		System.out.println("🔹 logoutService is null? " + (logoutService == null));

		if (sessionId == null || sessionId.isBlank()) {
			return ResponseEntity.badRequest().body(Map.of(
				"status", "failure",
				"message", "세션 ID가 누락되었습니다."
			));
		}

		boolean isLoggedOut = logoutService.invalidateSession(sessionId);
		System.out.println("🔹 Logout result: " + isLoggedOut);

		if (isLoggedOut) {
			return ResponseEntity.ok(Map.of(
				"status", "SUCCEED",
				"message", "로그아웃 되었습니다.",
				"data", Collections.emptyMap()
			));
		} else {
			return ResponseEntity.status(400).body(Map.of(
				"status", "failure",
				"message", "유효하지 않은 세션 ID입니다."
			));
		}
	}
}
