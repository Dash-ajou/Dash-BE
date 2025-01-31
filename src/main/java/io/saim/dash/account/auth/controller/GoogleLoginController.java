package io.saim.dash.account.auth.controller;

import io.saim.dash.account.auth.dto.GoogleInfoResponseDTO;
import io.saim.dash.account.auth.dto.GoogleResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class GoogleLoginController {

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleClientId;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String googleClientSecret;

	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String googleRedirectUri;

	private final RestTemplate restTemplate = new RestTemplate();

	//Google 로그인 URL 반환
	@PostMapping("/google")
	public ResponseEntity<String> loginUrlGoogle() {
		String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth"
			+ "?client_id=" + googleClientId
			+ "&redirect_uri=" + googleRedirectUri
			+ "&response_type=code"
			+ "&scope=openid%20email%20profile"
			+ "&access_type=offline";

		return ResponseEntity.ok(reqUrl);
	}

	//Google 리디렉션 처리
	@GetMapping("/google/callback")
	public ResponseEntity<?> handleGoogleCallback(@RequestParam("code") String code) {
		try {
			//Google 토큰 엔드포인트에 요청하여 액세스 토큰 받기
			GoogleResponseDTO googleResponse = requestAccessToken(code);

			//Google API를 호출하여 사용자 정보 가져오기
			GoogleInfoResponseDTO userInfo = fetchUserInfo(googleResponse.getAccess_token());

			//사용자 정보 반환 (추후 DB 저장 등 추가 처리 가능)
			return ResponseEntity.ok(userInfo);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Google OAuth2 처리 중 오류 발생: " + e.getMessage());
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

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		ResponseEntity<GoogleResponseDTO> response = restTemplate.postForEntity(tokenUrl, request, GoogleResponseDTO.class);

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
