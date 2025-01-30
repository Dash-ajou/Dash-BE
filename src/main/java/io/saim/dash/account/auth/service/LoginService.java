package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.dto.LoginResponseDTO;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final SignupNameRepository signupNameRepository;

	public LoginResponseDTO login(String generalPhone, String rawPassword) {
		SignupName user = signupNameRepository.findByGeneralPhone(generalPhone)
			.orElseThrow(() -> new IllegalArgumentException("등록되지 않은 전화번호입니다."));

		return new LoginResponseDTO(
			"success",
			"로그인 성공",
			new LoginResponseDTO.Data(
				new LoginResponseDTO.User(
					user.getGeneralName(),
					user.getGeneralEmail(),
					user.getGeneralPhone(),
					user.getGeneralType() // 필드 없으면 기본값 사용
				),
				generateSessionId() // ✅ 메서드 정상 호출
			)
		);
	}

	// ✅ 올바른 위치: 클래스 내부, 메서드 바깥에 코드 없음
	private String generateSessionId() {
		return UUID.randomUUID().toString();
	}
}
