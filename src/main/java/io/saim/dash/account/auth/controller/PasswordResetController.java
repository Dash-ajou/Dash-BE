package io.saim.dash.account.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.saim.dash.account.auth.dto.PasswordResetRequestDTO;
import io.saim.dash.account.auth.dto.PasswordResetResponseDTO;
import io.saim.dash.account.auth.dto.PhoneVerificationCheckDTO;
import io.saim.dash.account.auth.service.PhoneVerificationService;
import io.saim.dash.account.auth.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

	@Autowired
	private PhoneVerificationService phoneVerificationService;
	private final PasswordResetService passwordResetService;

	//비밀번호 재설정 인증 요청
	@PostMapping("/request")
	public ResponseEntity<?> requestPasswordReset(@RequestBody PhoneVerificationCheckDTO requestDTO) {
		String userPhone = requestDTO.getUserPhone();
		phoneVerificationService.requestVerification(userPhone);

		return ResponseEntity.ok(Map.of(
			"status", "success",
			"message", "Password reset verification code sent successfully."
		));
	}

	//비밀번호 재설정 인증 코드 검증
	@PostMapping("/verify")
	public ResponseEntity<?> verifyPasswordResetCode(@RequestBody PhoneVerificationCheckDTO checkDTO) {
		String userPhone = checkDTO.getUserPhone();
		String userVerifyCode = checkDTO.getUserVerifyCode();
		boolean isVerified = phoneVerificationService.verifyCode(userPhone, userVerifyCode);

		if (isVerified) {
			return ResponseEntity.ok(Map.of(
				"status", "success",
				"message", "Verification successful.",
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

	@PostMapping("/complete")
	public ResponseEntity<PasswordResetResponseDTO> resetPassword(@Valid @RequestBody PasswordResetRequestDTO requestDTO) {
		PasswordResetResponseDTO response = passwordResetService.resetPassword(requestDTO);
		return ResponseEntity.ok(response);
	}
}
