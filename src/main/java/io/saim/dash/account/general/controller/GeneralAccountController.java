package io.saim.dash.account.general.controller;

import java.util.Map;

import io.saim.dash.account.general.dto.*;
import io.saim.dash.account.general.service.GeneralAccountService;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import io.saim.dash.global.dto.APIStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import io.saim.dash.security.CustomUserDetails;

@RestController
@RequestMapping("/general")
@RequiredArgsConstructor
public class GeneralAccountController {

	private final GeneralAccountService generalAccountService;

	// 계정 상세 조회 기능
	@GetMapping("/account")
	public ResponseEntity<CommonResponseDTO<GeneralAccountResponseDTO.Data>> getGeneralAccountDetails(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null || userDetails.getUser() == null) {
			return ResponseEntity.status(401).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"인증되지 않은 사용자입니다. 로그인 후 다시 시도해주세요.",
				null
			));
		}

		try {
			GeneralAccountResponseDTO response = generalAccountService.getGeneralAccountDetails(userDetails.getUser());

			//데이터가 정상적으로 존재하는지 확인
			GeneralAccountResponseDTO.Data responseData = response.getData();
			if (responseData == null) {
				System.out.println("⚠️ [GeneralAccountController] response.getData()가 null입니다.");
				return ResponseEntity.status(500).body(new CommonResponseDTO<>(
					new VersionResponseDTO("1.0", "1.0"),
					APIStatus.FAILURE,
					"계정 정보를 가져오는 데 실패했습니다.",
					null
				));
			}

			return ResponseEntity.ok(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"계정 정보를 가져왔습니다.",
				responseData
			));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"서버 내부 오류가 발생했습니다.",
				null
			));
		}
	}

	// 신규 전화번호 변경 기능
	@PatchMapping("/account/phone")
	public ResponseEntity<CommonResponseDTO<?>> updatePhoneNumber(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody GeneralPhoneUpdateDTO updateDTO) {

		boolean isUpdated = generalAccountService.updatePhoneNumber(userDetails.getUser(), updateDTO);

		if (isUpdated) {
			return ResponseEntity.ok(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"전화번호가 성공적으로 변경되었습니다.",
				Map.of("general_new_phone", updateDTO.getGeneralNewPhone())
			));
		} else {
			return ResponseEntity.badRequest().body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"전화번호 변경에 실패했습니다. 인증 코드를 확인하세요.",
				null
			));
		}
	}

	// 이메일 변경 인증 요청
	@PostMapping("/account/email-verify/request")
	public ResponseEntity<CommonResponseDTO<?>> requestEmailVerification(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody Map<String, String> requestBody) {

		String newEmail = requestBody.get("new_email");

		if (newEmail == null || newEmail.isBlank()) {
			return ResponseEntity.badRequest().body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"새로운 이메일을 입력해야 합니다.",
				null
			));
		}

		boolean isSent = generalAccountService.requestEmailVerification(userDetails.getUser(), newEmail);

		if (isSent) {
			return ResponseEntity.ok(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"인증 코드가 이메일로 전송되었습니다.",
				Map.of("expires_in", 180) // 인증 코드 만료 시간
			));
		} else {
			return ResponseEntity.badRequest().body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"이메일 인증 요청을 실패했습니다.",
				null
			));
		}
	}

	// 이메일 변경 인증 코드 확인
	@PostMapping("/account/email-verify/confirm")
	public ResponseEntity<CommonResponseDTO<?>> confirmEmailVerification(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody GeneralEmailVerifyConfirmDTO confirmDTO) {

		boolean isConfirmed = generalAccountService.confirmEmailVerification(userDetails.getUser(), confirmDTO);

		if (isConfirmed) {
			return ResponseEntity.ok(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"인증이 완료되었습니다.",
				"이메일 인증이 성공적으로 완료되었습니다."
			));
		} else {
			return ResponseEntity.badRequest().body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"인증 코드가 올바르지 않거나 만료되었습니다.",
				"이메일 인증 실패"
			));
		}
	}

	// 회원 탈퇴
	@DeleteMapping("/account/delete")
	public ResponseEntity<CommonResponseDTO<?>> deleteAccount(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		boolean isDeleted = generalAccountService.deleteAccount(userDetails.getUser());

		if (isDeleted) {
			return ResponseEntity.ok(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"회원 탈퇴가 완료되었습니다.",
				null
			));
		} else {
			return ResponseEntity.status(403).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"회원 탈퇴에 실패했습니다. 유효한 세션이 아닙니다.",
				null
			));
		}
	}
}
