package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.dto.LoginResponseDTO;
import io.saim.dash.account.auth.session.SessionManager;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final SignupNameRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final SessionManager sessionManager;

	public LoginResponseDTO login(String generalPhone, String rawPassword) {
		//사용자 정보 조회
		SignupName user = signupNameRepository.findByGeneralPhone(generalPhone)
			.orElseThrow(() -> new IllegalArgumentException("등록되지 않은 전화번호입니다."));

		//가장 최근 비밀번호 가져오기
		List<Password> userPasswords = passwordRepository.findByUser(user);
		Password userPassword = Password.getLatestPassword(userPasswords);
		if (userPassword == null) {
			throw new IllegalArgumentException("비밀번호가 설정되지 않았습니다.");
		}

		//비밀번호 검증
		if (!user.isPasswordValid(rawPassword, userPassword, passwordEncoder)) {
			throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
		}

		//세션 생성 및 저장
		String sessionId = generateSessionId();
		sessionManager.createSession(sessionId, user.getGeneralId().toString()); //세션 저장
		System.out.println("로그인 성공: 세션 생성됨 → " + sessionId);

		return new LoginResponseDTO(
			"success",
			"로그인 성공",
			new LoginResponseDTO.Data(
				new LoginResponseDTO.User(
					user.getGeneralName(),
					user.getGeneralEmail(),
					user.getGeneralPhone(),
					user.getGeneralType()
				),
				sessionId //세션 ID 생성
			)
		);
	}

	//세션 ID 생성 로직
	private String generateSessionId() {
		return UUID.randomUUID().toString();
	}
}
