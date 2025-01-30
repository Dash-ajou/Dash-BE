package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.dto.LoginResponseDTO;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final SignupNameRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public LoginResponseDTO login(String generalPhone, String rawPassword) {
		//사용자 정보 조회 (전화번호 기반)
		SignupName user = signupNameRepository.findByGeneralPhone(generalPhone)
			.orElseThrow(() -> new IllegalArgumentException("등록되지 않은 전화번호입니다."));

		//비밀번호 조회 (해당 사용자 ID를 기반으로)
		Password userPassword = passwordRepository.findByUser(user)
			.orElseThrow(() -> new IllegalArgumentException("비밀번호가 설정되지 않았습니다."));

		//입력한 비밀번호와 DB의 해싱된 비밀번호 비교
		if (!passwordEncoder.matches(rawPassword, userPassword.getHashedPassword())) {
			throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
		}

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
				generateSessionId()
			)
		);
	}

	private String generateSessionId() {
		return UUID.randomUUID().toString();
	}
}
