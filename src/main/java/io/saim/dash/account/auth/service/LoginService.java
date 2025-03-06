package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.dto.LoginResponseDTO;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import io.saim.dash.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final SignupNameRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public LoginResponseDTO login(String generalPhone, String rawPassword, HttpSession session) {
		//사용자 정보 조회
		GeneralUser user = signupNameRepository.findByGeneralPhone(generalPhone)
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

		//Spring Security UserDetails 생성
		CustomUserDetails userDetails = new CustomUserDetails(user);

		//Authentication 객체 생성
		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		//SecurityContext에 인증 정보 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);

		//세션 생성 및 ID 저장
		String sessionId = session.getId();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

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
				sessionId
			)
		);
	}
}
