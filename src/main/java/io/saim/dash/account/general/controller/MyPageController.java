package io.saim.dash.account.general.controller;

import io.saim.dash.account.general.dto.MyPageResponseDTO;
import io.saim.dash.account.general.service.MyPageService;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/general")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping("/mypage")
	public ResponseEntity<MyPageResponseDTO> getMyPage(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (userDetails == null) {
			throw new IllegalStateException("인증된 사용자가 아닙니다. 로그인 후 다시 시도해주세요.");
		}

		MyPageResponseDTO response = myPageService.getMyPageInfo(userDetails.getUser());
		return ResponseEntity.ok(response);
	}
}
