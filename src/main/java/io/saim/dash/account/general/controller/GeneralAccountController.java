package io.saim.dash.account.general.controller;

import java.util.Map;

import io.saim.dash.account.general.dto.GeneralAccountResponseDTO;
import io.saim.dash.account.general.dto.GeneralEmailVerifyConfirmDTO;
import io.saim.dash.account.general.dto.GeneralEmailVerifyRequestDTO;
import io.saim.dash.account.general.dto.GeneralPhoneUpdateDTO;
import io.saim.dash.account.general.service.GeneralAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/general")
@RequiredArgsConstructor
public class GeneralAccountController {

	private final GeneralAccountService generalAccountService;

	//계정 상세 조회 기능
	@GetMapping("/account")
	public ResponseEntity<GeneralAccountResponseDTO> getGeneralAccountDetails(@RequestHeader("Authorization") String token) {
		GeneralAccountResponseDTO response = generalAccountService.getGeneralAccountDetails(token);
		return ResponseEntity.ok(response);
	}

	//신규 전화번호 변경 기능
	@PatchMapping("/account/phone")
	public ResponseEntity<?> updatePhoneNumber(
		@RequestHeader("Authorization") String sessionId,
		@RequestBody GeneralPhoneUpdateDTO updateDTO) {
		boolean isUpdated = generalAccountService.updatePhoneNumber(sessionId, updateDTO);

		if (isUpdated) {
			return ResponseEntity.ok(Map.of(
				"status", "SUCCESS",
				"message", "전화번호가 성공적으로 변경되었습니다.",
				"data", Map.of("general_new_phone", updateDTO.getGeneralNewPhone())
			));
		} else {
			return ResponseEntity.status(400).body(Map.of(
				"status", "failure",
				"message", "전화번호 변경에 실패했습니다. 인증 코드를 확인하세요."
			));
		}
	}

	//이메일 변경 인증 요청
	@PostMapping("/account/email-verify/request")
	public ResponseEntity<?> requestEmailVerification(
		@RequestHeader("Authorization") String sessionId,
		@RequestBody Map<String, String> requestBody) {

		String newEmail = requestBody.get("new_email");

		if (newEmail == null || newEmail.isBlank()) {
			return ResponseEntity.badRequest().body(Map.of(
				"status", "failure",
				"message", "새로운 이메일을 입력해야 합니다."
			));
		}

		boolean isSent = generalAccountService.requestEmailVerification(sessionId, newEmail);

		if (isSent) {
			return ResponseEntity.ok(Map.of(
				"status", "SUCCESS",
				"message", "인증 코드가 이메일로 전송되었습니다.",
				"data", Map.of("expires_in", 180)
			));
		} else {
			return ResponseEntity.status(400).body(Map.of(
				"status", "failure",
				"message", "이메일 인증 요청을 실패했습니다."
			));
		}
	}

	//이메일 변경 인증 코드 확인
	@PostMapping("/account/email-verify/confirm")
	public ResponseEntity<?> confirmEmailVerification(
		@RequestHeader("Authorization") String sessionId,
		@RequestBody GeneralEmailVerifyConfirmDTO confirmDTO) {

		boolean isConfirmed = generalAccountService.confirmEmailVerification(sessionId, confirmDTO);

		if (isConfirmed) {
			return ResponseEntity.ok(Map.of(
				"status", "SUCCESS",
				"message", "인증이 완료되었습니다."
			));
		} else {
			return ResponseEntity.status(400).body(Map.of(
				"status", "failure",
				"message", "인증 코드가 올바르지 않거나 만료되었습니다."
			));
		}
	}

	//회원 탈퇴
	@DeleteMapping("/account/delete")
	public ResponseEntity<?> deleteAccount(@RequestHeader("Authorization") String sessionId) {
		boolean isDeleted = generalAccountService.deleteAccount(sessionId);

		if (isDeleted) {
			return ResponseEntity.ok(Map.of(
				"status", "SUCCESS",
				"message", "회원 탈퇴가 완료되었습니다."
			));
		} else {
			return ResponseEntity.status(403).body(Map.of(
				"status", "FAILED",
				"message", "회원 탈퇴에 실패했습니다. 유효한 세션이 아닙니다."
			));
		}
	}
}
