package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.dto.PasswordResetRequestDTO;
import io.saim.dash.account.auth.dto.PasswordResetResponseDTO;
import io.saim.dash.account.auth.model.PhoneVerification;
import io.saim.dash.account.auth.repository.PhoneVerificationRepository;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

	private final SignupNameRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final PhoneVerificationRepository phoneVerificationRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public PasswordResetResponseDTO resetPassword(PasswordResetRequestDTO requestDTO) {
		//전화번호 인증 여부 확인
		PhoneVerification verification = phoneVerificationRepository.findByUserPhone(requestDTO.getUserPhone())
			.orElseThrow(() -> new IllegalArgumentException("해당 전화번호의 인증 요청이 없습니다."));

		if (!verification.isUserVerified()) {
			throw new IllegalArgumentException("전화번호 인증이 완료되지 않았습니다.");
		}

		//사용자 조회
		SignupName user = signupNameRepository.findByGeneralPhone(requestDTO.getUserPhone())
			.orElseThrow(() -> new IllegalArgumentException("등록되지 않은 전화번호입니다."));

		//가장 최근 비밀번호 가져오기
		List<Password> userPasswords = passwordRepository.findByUser(user);
		Password password = userPasswords.stream()
			.max((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt())) //최신 비밀번호 찾기
			.orElse(new Password()); // 🔹 기본 비밀번호 객체 생성

		//비밀번호 확인 일치 여부 검증
		if (!requestDTO.getNewPassword().equals(requestDTO.getNewPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		//비밀번호 변경을 Password 엔티티 내에서 수행하도록 변경
		password.setUser(user);
		password.changePassword(requestDTO.getNewPassword(), passwordEncoder);
		passwordRepository.save(password);

		return new PasswordResetResponseDTO("SUCCESS", "비밀번호가 성공적으로 재설정되었습니다.");
	}
}
