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

		//공통 응답 형식 적용
		CommonResponseDTO<?> response = new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"Verification code sent successfully.",
			new PhoneVerificationResponse(300, LocalDateTime.now()) // data 필드
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
				"Phone number verified.",
				new VerificationResult(userPhone, true)
			);
			return ResponseEntity.ok(response);
		} else {
			CommonResponseDTO<?> response = new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.BAD_REQUEST,
				"Invalid or expired verification code.",
				null
			);
			return ResponseEntity.badRequest().body(response);
		}
	}

	//요청 코드 응답 데이터 클래스
	private record PhoneVerificationResponse(int expires_in, LocalDateTime request_time) {}

	//인증 확인 응답 데이터 클래스
	private record VerificationResult(String user_phone, boolean user_verified) {}

}
