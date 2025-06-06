package io.saim.dash.account.general.controller;

import io.saim.dash.account.general.dto.MyPageResponseDTO;
import io.saim.dash.account.general.service.MyPageService;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/general")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping("/mypage")
	public ResponseEntity<CommonResponseDTO<MyPageResponseDTO>> getMyPage(@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null) {
			throw new IllegalStateException("인증된 사용자가 아닙니다. 로그인 후 다시 시도해주세요.");
		}

		// 일반 사용자 정보 가져오기
		MyPageResponseDTO responseData = myPageService.getMyPageInfo(userDetails.getGeneralUser());

		CommonResponseDTO<MyPageResponseDTO> response = new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"계정 정보를 성공적으로 가져왔습니다.",
			responseData
		);

		return ResponseEntity.ok(response);
	}
}
