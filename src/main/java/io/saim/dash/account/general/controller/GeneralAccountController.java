package io.saim.dash.account.general.controller;

import java.util.Map;

import io.saim.dash.account.general.dto.GeneralAccountResponseDTO;
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
}
