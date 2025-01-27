package io.saim.dash.account.auth.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.saim.dash.account.auth.dto.PhoneVerificationRequestDTO;
import io.saim.dash.account.auth.dto.PhoneVerificationCheckDTO;
import io.saim.dash.account.auth.service.PhoneVerificationService;

@RestController
@RequestMapping("/auth/phone")
public class PhoneVerificationController {

	@Autowired
	private PhoneVerificationService phoneVerificationService;

	@PostMapping("/request")
	public ResponseEntity<?> requestVerification(@RequestBody PhoneVerificationRequestDTO requestDTO) {
		String userPhone = requestDTO.getUserPhone();
		String userVerifyCode = phoneVerificationService.requestVerification(userPhone);

		return ResponseEntity.ok(Map.of(
			"status", "success",
			"message", "Verification code sent successfully.",
			"data", Map.of(
				"expires_in", 300,
				"request_time", LocalDateTime.now()
			)
		));
	}

	@PostMapping("/verify")
	public ResponseEntity<?> verifyCode(@RequestBody PhoneVerificationCheckDTO checkDTO) {
		String userPhone = checkDTO.getUserPhone();
		String userVerifyCode = checkDTO.getUserVerifyCode();
		boolean isVerified = phoneVerificationService.verifyCode(userPhone, userVerifyCode);

		if (isVerified) {
			return ResponseEntity.ok(Map.of(
				"status", "success",
				"message", "Phone number verified.",
				"data", Map.of(
					"user_phone", userPhone,
					"user_verified", true
				)
			));
		} else {
			return ResponseEntity.status(400).body(Map.of(
				"status", "failure",
				"message", "Invalid or expired verification code."
			));
		}
	}
}
