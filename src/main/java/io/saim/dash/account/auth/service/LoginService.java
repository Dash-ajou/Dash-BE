package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.dto.LoginResponseDTO;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.GeneralUserRepository;
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

	private final GeneralUserRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final PartnerUserRepository partnerUserRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public LoginResponseDTO login(String userPhone, String rawPassword, HttpSession session) {
		String requestedUserType = (String) session.getAttribute("user_type");

		if (requestedUserType == null || requestedUserType.isBlank()) {
			Optional<GeneralUser> generalUserOpt = signupNameRepository.findByPhone(userPhone);
			if (generalUserOpt.isPresent()) {
				session.setAttribute("user_type", "GENERAL");
				return authenticateGeneralUser(generalUserOpt.get(), rawPassword, session);
			}

			Optional<PartnerUser> partnerUserOpt = partnerUserRepository.findByPhone(userPhone);
			if (partnerUserOpt.isPresent()) {
				session.setAttribute("user_type", "PARTNER");
				return authenticatePartnerUser(partnerUserOpt.get(), rawPassword, session);
			}

			throw new IllegalArgumentException("등록되지 않은 전화번호입니다.");
		}

		if ("GENERAL".equalsIgnoreCase(requestedUserType)) {
			GeneralUser generalUser = signupNameRepository.findByPhone(userPhone)
				.orElseThrow(() -> new IllegalArgumentException("일반 사용자로 등록되지 않은 전화번호입니다."));
			return authenticateGeneralUser(generalUser, rawPassword, session);

		} else if ("PARTNER".equalsIgnoreCase(requestedUserType)) {
			PartnerUser partnerUser = partnerUserRepository.findByPhone(userPhone)
				.orElseThrow(() -> new IllegalArgumentException("파트너 사용자로 등록되지 않은 전화번호입니다."));
			return authenticatePartnerUser(partnerUser, rawPassword, session);

		} else {
			throw new IllegalArgumentException("지원하지 않는 사용자 타입입니다.");
		}
	}

	private LoginResponseDTO authenticateGeneralUser(GeneralUser user, String rawPassword, HttpSession session) {
		List<Password> userPasswords = passwordRepository.findByUser(user);
		Password userPassword = Password.getLatestPassword(userPasswords);
		if (userPassword == null || !user.isPasswordValid(rawPassword, userPassword, passwordEncoder)) {
			throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
		}

		setAuthentication(user);
		session.setAttribute("userType", "GENERAL");
		session.setAttribute("userId", user.getId());

		return createLoginResponse(user.getName(), user.getEmail(), user.getPhone(), "GENERAL", session);
	}

	private LoginResponseDTO authenticatePartnerUser(PartnerUser user, String rawPassword, HttpSession session) {
		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
		}

		setAuthentication(user);
		session.setAttribute("userType", "PARTNER");
		session.setAttribute("userId", user.getId());

		return createLoginResponse(user.getOwnerName(), user.getEmail(), user.getPhone(), "PARTNER", session);
	}

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
