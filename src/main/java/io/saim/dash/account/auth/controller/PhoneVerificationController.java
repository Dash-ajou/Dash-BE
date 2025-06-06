package io.saim.dash.account.auth.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.saim.dash.account.auth.dto.PhoneVerificationRequestDTO;
import io.saim.dash.account.auth.dto.PhoneVerificationCheckDTO;
import io.saim.dash.account.auth.service.PhoneVerificationService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;

@RestController
@RequestMapping("/auth/phone")
public class PhoneVerificationController {

	@Autowired
	private PhoneVerificationService phoneVerificationService;

	@PostMapping("/request")
	public ResponseEntity<CommonResponseDTO<?>> requestVerification(@RequestBody PhoneVerificationRequestDTO requestDTO) {
		String userPhone = requestDTO.getUserPhone();
		String userVerifyCode = phoneVerificationService.requestVerification(userPhone);

		CommonResponseDTO<?> response = new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"인증번호가 전송되었습니다.",
			new PhoneVerificationResponse(300, LocalDateTime.now())
		);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/verify")
	public ResponseEntity<CommonResponseDTO<?>> verifyCode(@RequestBody PhoneVerificationCheckDTO checkDTO) {
		String userPhone = checkDTO.getUserPhone();
		String userVerifyCode = checkDTO.getUserVerifyCode();
		boolean isVerified = phoneVerificationService.verifyCode(userPhone, userVerifyCode);

		if (isVerified) {
			CommonResponseDTO<?> response = new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"전화번호 인증에 성공했습니다.",
				new VerificationResult(userPhone, true)
			);
			return ResponseEntity.ok(response);
		} else {
			CommonResponseDTO<?> response = new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.BAD_REQUEST,
				"인증번호가 만료되었거나 올바르지 않습니다.",
				null
			);
			return ResponseEntity.badRequest().body(response);
		}
	}

	private record PhoneVerificationResponse(int expiresIn, LocalDateTime requestTime) {}
	private record VerificationResult(String userPhone, boolean verified) {}
}
