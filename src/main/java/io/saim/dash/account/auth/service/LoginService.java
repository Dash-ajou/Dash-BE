package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.dto.LoginResponseDTO;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final SignupNameRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final PartnerUserRepository partnerUserRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public LoginResponseDTO login(String userPhone, String rawPassword, HttpSession session) {
		GeneralUser generalUser = null;
		PartnerUser partnerUser = null;

		//일반 사용자 확인
		Optional<GeneralUser> generalUserOpt = signupNameRepository.findByGeneralPhone(userPhone);
		if (generalUserOpt.isPresent()) {
			generalUser = generalUserOpt.get();
		}

		//파트너 사용자 확인
		Optional<PartnerUser> partnerUserOpt = partnerUserRepository.findByOwnerPhone(userPhone);
		if (partnerUserOpt.isPresent()) {
			partnerUser = partnerUserOpt.get();
		}

		//존재하는 사용자 체크
		if (generalUser == null && partnerUser == null) {
			throw new IllegalArgumentException("등록되지 않은 전화번호입니다.");
		}

		//비밀번호 검증
		if (generalUser != null) { //일반 사용자 로그인
			return authenticateGeneralUser(generalUser, rawPassword, session);
		} else { //파트너 사용자 로그인
			return authenticatePartnerUser(partnerUser, rawPassword, session);
		}
	}

	//일반 사용자 로그인 처리
	private LoginResponseDTO authenticateGeneralUser(GeneralUser user, String rawPassword, HttpSession session) {
		//최신 비밀번호 가져오기
		List<Password> userPasswords = passwordRepository.findByUser(user);
		Password userPassword = Password.getLatestPassword(userPasswords);
		if (userPassword == null || !user.isPasswordValid(rawPassword, userPassword, passwordEncoder)) {
			throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
		}

		//Spring Security 인증 설정
		setAuthentication(user);

		//세션에 사용자 정보 저장
		session.setAttribute("userType", "GENERAL");
		session.setAttribute("userId", user.getGeneralId());

		return createLoginResponse(user.getGeneralName(), user.getGeneralEmail(), user.getGeneralPhone(), "GENERAL", session);
	}

	//파트너 사용자 로그인 처리
	private LoginResponseDTO authenticatePartnerUser(PartnerUser user, String rawPassword, HttpSession session) {
		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
		}

		//Spring Security 인증 설정
		setAuthentication(user);

		//세션에 사용자 정보 저장
		session.setAttribute("userType", "PARTNER");
		session.setAttribute("userId", user.getPartnerId());

		return createLoginResponse(user.getOwnerName(), user.getOwnerEmail(), user.getOwnerPhone(), "PARTNER", session);
	}

	//Spring Security 인증 설정
	private void setAuthentication(Object user) {
		CustomUserDetails userDetails;

		if (user instanceof GeneralUser) {
			userDetails = new CustomUserDetails((GeneralUser) user);
		} else if (user instanceof PartnerUser) {
			userDetails = new CustomUserDetails((PartnerUser) user);
		} else {
			throw new IllegalArgumentException("올바르지 않은 사용자 타입입니다.");
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	//로그인 응답 생성
	private LoginResponseDTO createLoginResponse(String name, String email, String phone, String userType, HttpSession session) {
		return new LoginResponseDTO(
			"success",
			"로그인 성공",
			new LoginResponseDTO.Data(
				new LoginResponseDTO.User(name, email, phone, userType),
				session.getId()
			)
		);
	}
}
