package io.saim.dash.account.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.saim.dash.account.auth.dto.PasswordResetRequestDTO;
import io.saim.dash.account.auth.dto.PasswordResetResponseDTO;
import io.saim.dash.account.auth.dto.PhoneVerificationCheckDTO;
import io.saim.dash.account.auth.service.PhoneVerificationService;
import io.saim.dash.account.auth.service.PasswordResetService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

	@Autowired
	private PhoneVerificationService phoneVerificationService;
	private final PasswordResetService passwordResetService;

	// 비밀번호 재설정 인증 요청
	@PostMapping("/request")
	public ResponseEntity<CommonResponseDTO<?>> requestPasswordReset(@RequestBody PhoneVerificationCheckDTO requestDTO) {
		String userPhone = requestDTO.getUserPhone();
		phoneVerificationService.requestVerification(userPhone);

		CommonResponseDTO<?> response = new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"비밀번호 재설정을 위한 인증번호가 전송되었습니다.",
			null
		);

		return ResponseEntity.ok(response);
	}

	// 비밀번호 재설정 인증 코드 검증
	@PostMapping("/verify")
	public ResponseEntity<CommonResponseDTO<?>> verifyPasswordResetCode(@RequestBody PhoneVerificationCheckDTO checkDTO) {
		String userPhone = checkDTO.getUserPhone();
		String userVerifyCode = checkDTO.getUserVerifyCode();
		boolean isVerified = phoneVerificationService.verifyCode(userPhone, userVerifyCode);

		if (isVerified) {
			CommonResponseDTO<?> response = new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"인증에 성공했습니다.",
				new VerificationResult(userPhone, true)
			);
			return ResponseEntity.ok(response);
		} else {
			CommonResponseDTO<?> response = new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.BAD_REQUEST,
				"인증번호가 올바르지 않거나 만료되었습니다.",
				null
			);
			return ResponseEntity.badRequest().body(response);
		}
	}

	// 비밀번호 재설정 완료
	@PostMapping("/complete")
	public ResponseEntity<CommonResponseDTO<PasswordResetResponseDTO>> resetPassword(
		@Valid @RequestBody PasswordResetRequestDTO requestDTO) {

		passwordResetService.resetPassword(requestDTO);

		CommonResponseDTO<PasswordResetResponseDTO> response = new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"비밀번호가 성공적으로 재설정되었습니다.",
			null
		);

		return ResponseEntity.ok(response);
	}

	private record VerificationResult(String user_phone, boolean user_verified) {}
}
