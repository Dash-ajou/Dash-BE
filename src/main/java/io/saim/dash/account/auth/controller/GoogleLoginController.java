package io.saim.dash.account.auth.controller;

import java.net.URI;
import java.util.Map;
import io.saim.dash.account.auth.dto.GoogleInfoResponseDTO;
import io.saim.dash.account.auth.dto.GoogleResponseDTO;
import io.saim.dash.account.auth.dto.GoogleTokenVerifyResponseDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth")
public class GoogleLoginController {

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleClientId;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String googleClientSecret;

	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String googleRedirectUri;

	private final RestTemplate restTemplate = new RestTemplate();

	@PostMapping("/google")
	public ResponseEntity<?> verifyGoogleToken(
		@RequestBody Map<String, String> body,
		HttpSession session) {

		String accessToken = body.get("google_access_token");
		GoogleInfoResponseDTO userInfo = fetchUserInfo(accessToken);

		session.setAttribute("user", userInfo);

		return ResponseEntity.ok(
			new GoogleTokenVerifyResponseDTO(
				"SUCCEED",
				"구글 인증이 완료되었습니다.",
				new GoogleTokenVerifyResponseDTO.Data(
					userInfo.getSub(),
					userInfo.getEmail(),
					userInfo.isEmail_verified()
				)
			)
		);
	}

	@GetMapping("/google/callback")
	public ResponseEntity<?> handleGoogleCallback(@RequestParam("code") String code) {
		try {
			//토큰 요청
			GoogleResponseDTO googleResponse = requestAccessToken(code);

			//사용자 정보 요청
			GoogleInfoResponseDTO userInfo = fetchUserInfo(googleResponse.getAccess_token());

			//액세스 토큰과 사용자 정보를 함께 반환
			return ResponseEntity.ok(
				Map.of(
					"status", "SUCCESS",
					"message", "Google 인증 완료 및 access token 발급",
					"data", Map.of(
						"access_token", googleResponse.getAccess_token(),
						"user", Map.of(
							"sub", userInfo.getSub(),
							"email", userInfo.getEmail(),
							"email_verified", userInfo.isEmail_verified(),
							"name", userInfo.getName(),
							"picture", userInfo.getPicture()
						)
					)
				)
			);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of(
					"status", "FAIL",
					"message", "Google OAuth2 처리 중 오류 발생",
					"error", e.getMessage()
				));
		}
	}

	//Google 토큰 요청
	private GoogleResponseDTO requestAccessToken(String code) {
		String tokenUrl = "https://oauth2.googleapis.com/token";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// MultiValueMap으로 URL-Encoded 요청 생성
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", code);
		body.add("client_id", googleClientId);
		body.add("client_secret", googleClientSecret);
		body.add("redirect_uri", googleRedirectUri);
		body.add("grant_type", "authorization_code");

		RequestEntity<MultiValueMap<String, String>> request = RequestEntity
			.post(URI.create(tokenUrl))
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(body);

		ResponseEntity<GoogleResponseDTO> response = restTemplate.exchange(request, GoogleResponseDTO.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Google OAuth2 Token 요청 실패: " + response.getStatusCode());
		}

		return response.getBody();
	}

	//Google 사용자 정보 요청
	private GoogleInfoResponseDTO fetchUserInfo(String accessToken) {
		String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<GoogleInfoResponseDTO> response = restTemplate.exchange(
			userInfoUrl, HttpMethod.GET, request, GoogleInfoResponseDTO.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Google 사용자 정보 요청 실패: " + response.getStatusCode());
		}

		return response.getBody();
	}
}
