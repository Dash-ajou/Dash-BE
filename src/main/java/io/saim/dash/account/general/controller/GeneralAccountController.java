package io.saim.dash.account.general.controller;

import java.util.List;
import java.util.Map;

import io.saim.dash.account.general.dto.*;
import io.saim.dash.account.general.service.GeneralAccountService;
import io.saim.dash.account.general.model.GeneralUser;
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

	@GetMapping("/account")
	public ResponseEntity<CommonResponseDTO<GeneralAccountResponseDTO.Data>> getGeneralAccountDetails(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null || !"GENERAL".equals(userDetails.getUserType())) {
			return ResponseEntity.status(401).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"인증되지 않은 일반 사용자입니다. 로그인 후 다시 시도해주세요.",
				null
			));
		}

		//GeneralUser 가져오기
		GeneralUser generalUser = userDetails.getGeneralUser();
		GeneralAccountResponseDTO response = generalAccountService.getGeneralAccountDetails(generalUser);
		GeneralAccountResponseDTO.Data data = response.getData();

		if (data.getGeneralEmail() != null && data.getGeneralEmail().endsWith("@dummy.com")) {
			data.setGeneralEmail(null);
		}

		return ResponseEntity.ok(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"계정 정보를 가져왔습니다.",
			data
		));
	}

	@PatchMapping("/account/phone")
	public ResponseEntity<CommonResponseDTO<?>> updatePhoneNumber(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody GeneralPhoneUpdateDTO updateDTO) {

		if (userDetails == null || !"GENERAL".equals(userDetails.getUserType())) {
			return ResponseEntity.status(401).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"인증되지 않은 일반 사용자입니다.",
				null
			));
		}

		GeneralUser generalUser = userDetails.getGeneralUser();
		boolean isUpdated = generalAccountService.updatePhoneNumber(generalUser, updateDTO);

		return isUpdated
			? ResponseEntity.ok(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"전화번호가 성공적으로 변경되었습니다.",
			Map.of("general_new_phone", updateDTO.getGeneralNewPhone())
		))
			: ResponseEntity.badRequest().body(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.FAILED,
			"전화번호 변경에 실패했습니다. 인증 코드를 확인하세요.",
			null
		));
	}

	@PostMapping("/account/email-verify/request")
	public ResponseEntity<CommonResponseDTO<?>> requestEmailVerification(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody Map<String, String> requestBody) {

		if (userDetails == null || !"GENERAL".equals(userDetails.getUserType())) {
			return ResponseEntity.status(401).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"인증되지 않은 일반 사용자입니다.",
				null
			));
		}

		GeneralUser generalUser = userDetails.getGeneralUser();
		String newEmail = requestBody.get("new_email");

		if (newEmail == null || newEmail.isBlank()) {
			return ResponseEntity.badRequest().body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"새로운 이메일을 입력해야 합니다.",
				null
			));
		}

		List<String> allowedDomains = List.of("@gmail.com", "@ajou.ac.kr");
		boolean isAllowed = allowedDomains.stream().anyMatch(domain -> newEmail.toLowerCase().endsWith(domain));

		if (!isAllowed) {
			return ResponseEntity.badRequest().body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"구글 계정만 이메일로 사용할 수 있습니다.",
				null
			));
		}

		boolean isSent = generalAccountService.requestEmailVerification(generalUser, newEmail);

		return isSent
			? ResponseEntity.ok(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"인증 코드가 이메일로 전송되었습니다.",
			Map.of("expires_in", 180)
		))
			: ResponseEntity.badRequest().body(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.FAILED,
			"이메일 인증 요청을 실패했습니다.",
			null
		));
	}

	@PostMapping("/account/email-verify/confirm")
	public ResponseEntity<CommonResponseDTO<?>> confirmEmailVerification(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody Map<String, String> requestBody) {

		if (userDetails == null || !"GENERAL".equals(userDetails.getUserType())) {
			return ResponseEntity.status(401).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"인증되지 않은 일반 사용자입니다. 로그인 후 다시 시도해주세요.",
				null
			));
		}

		GeneralUser generalUser = userDetails.getGeneralUser();
		if (generalUser == null) {
			return ResponseEntity.status(500).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"사용자 정보를 가져오는 데 실패했습니다.",
				null
			));
		}

		//요청에서 필요한 값 추출
		String newEmail = requestBody.get("new_email");
		String verificationCode = requestBody.get("email_verify_code");

		if (newEmail == null || newEmail.isBlank() || verificationCode == null || verificationCode.isBlank()) {
			return ResponseEntity.badRequest().body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"이메일 및 인증 코드를 입력해야 합니다.",
				null
			));
		}

		//DTO 객체 생성하여 전달
		GeneralEmailVerifyConfirmDTO confirmDTO = new GeneralEmailVerifyConfirmDTO();
		confirmDTO.setNewEmail(newEmail);
		confirmDTO.setEmailVerifyCode(verificationCode);

		boolean isVerified = generalAccountService.confirmEmailVerification(generalUser, confirmDTO);

		return isVerified
			? ResponseEntity.ok(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"이메일 인증이 완료되었습니다.",
			null
		))
			: ResponseEntity.badRequest().body(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.FAILED,
			"이메일 인증에 실패했습니다. 인증 코드를 확인하세요.",
			null
		));
	}

	@DeleteMapping("/account/delete")
	public ResponseEntity<CommonResponseDTO<?>> deleteAccount(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null || !"GENERAL".equals(userDetails.getUserType())) {
			return ResponseEntity.status(401).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"인증되지 않은 일반 사용자입니다.",
				null
			));
		}

		GeneralUser generalUser = userDetails.getGeneralUser();
		boolean isDeleted = generalAccountService.deleteAccount(generalUser);

		return isDeleted
			? ResponseEntity.ok(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"회원 탈퇴가 완료되었습니다.",
			null
		))
			: ResponseEntity.status(403).body(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.FAILED,
			"회원 탈퇴에 실패했습니다. 유효한 세션이 아닙니다.",
			null
		));
	}
}
