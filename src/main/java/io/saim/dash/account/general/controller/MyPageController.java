package io.saim.dash.account.general.controller;

import io.saim.dash.account.general.dto.MyPageResponseDTO;
import io.saim.dash.account.general.service.MyPageService;
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
	public ResponseEntity<MyPageResponseDTO> getMyPage(@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null) {
			throw new IllegalStateException("인증된 사용자가 아닙니다. 로그인 후 다시 시도해주세요.");
		}

		//일반 사용자 정보 가져오기
		MyPageResponseDTO response = myPageService.getMyPageInfo(userDetails.getGeneralUser());
		return ResponseEntity.ok(response);
	}
}
