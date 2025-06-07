package io.saim.dash.account.auth.controller;

import io.saim.dash.account.auth.dto.GoogleInfoResponseDTO;
import io.saim.dash.account.auth.dto.GoogleResponseDTO;
import io.saim.dash.account.auth.dto.GoogleTokenVerifyResponseDTO;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class GoogleLoginController {

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleClientId;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String googleClientSecret;

	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String googleRedirectUri;
	private final WebClient webClient = WebClient.create();

	@PostMapping("/google")
	public ResponseEntity<CommonResponseDTO<?>> verifyGoogleToken(
		@RequestBody Map<String, String> body,
		HttpSession session
	) {
		String accessToken = body.get("google_access_token");
		GoogleInfoResponseDTO userInfo = fetchUserInfo(accessToken);
		session.setAttribute("user", userInfo);

		GoogleTokenVerifyResponseDTO.Data data = new GoogleTokenVerifyResponseDTO.Data(
			userInfo.getSub(),
			userInfo.getEmail(),
			userInfo.isEmail_verified()
		);

		return ResponseEntity.ok(
			new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"구글 인증이 완료되었습니다.",
				data
			)
		);
	}

	@GetMapping("/google/callback")
	public ResponseEntity<CommonResponseDTO<?>> handleGoogleCallback(@RequestParam("code") String code) {
		try {
			GoogleResponseDTO googleResponse = requestAccessToken(code);
			GoogleInfoResponseDTO userInfo = fetchUserInfo(googleResponse.getAccess_token());

			Map<String, Object> userData = Map.of(
				"sub", userInfo.getSub(),
				"email", userInfo.getEmail(),
				"email_verified", userInfo.isEmail_verified(),
				"name", userInfo.getName(),
				"picture", userInfo.getPicture()
			);

			Map<String, Object> data = Map.of(
				"access_token", googleResponse.getAccess_token(),
				"user", userData
			);

			return ResponseEntity.ok(
				new CommonResponseDTO<>(
					new VersionResponseDTO("1.0", "1.0"),
					APIStatus.SUCCESS,
					"Google 인증 완료 및 access token 발급",
					data
				)
			);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(
				new CommonResponseDTO<>(
					new VersionResponseDTO("1.0", "1.0"),
					APIStatus.FAILED,
					"Google OAuth2 처리 중 오류 발생",
					Map.of("error", e.getMessage())
				)
			);
		}
	}

	private GoogleResponseDTO requestAccessToken(String code) {
		return webClient.post()
			.uri("https://oauth2.googleapis.com/token")
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.body(BodyInserters.fromFormData("code", code)
				.with("client_id", googleClientId)
				.with("client_secret", googleClientSecret)
				.with("redirect_uri", googleRedirectUri)
				.with("grant_type", "authorization_code"))
			.retrieve()
			.bodyToMono(GoogleResponseDTO.class)
			.block();
	}

	private GoogleInfoResponseDTO fetchUserInfo(String accessToken) {
		return webClient.get()
			.uri("https://www.googleapis.com/oauth2/v3/userinfo")
			.headers(headers -> headers.setBearerAuth(accessToken))
			.retrieve()
			.bodyToMono(GoogleInfoResponseDTO.class)
			.block();
	}
}
