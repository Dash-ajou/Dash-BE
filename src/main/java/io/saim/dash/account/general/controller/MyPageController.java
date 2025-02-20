package io.saim.dash.account.general.controller;

import io.saim.dash.account.general.dto.MyPageResponseDTO;
import io.saim.dash.account.general.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/general")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping("/mypage")
	public ResponseEntity<MyPageResponseDTO> getMyPage(@RequestHeader("Authorization") String sessionId) {
		MyPageResponseDTO response = myPageService.getMyPageInfo(sessionId);
		return ResponseEntity.ok(response);
	}
}
