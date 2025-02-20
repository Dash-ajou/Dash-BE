package io.saim.dash.account.general.service;

import io.saim.dash.account.general.dto.GeneralPasswordRequestDTO;
import io.saim.dash.account.general.dto.GeneralPasswordResponseDTO;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignupPasswordService {

	private final SignupNameRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public GeneralPasswordResponseDTO setPassword(GeneralPasswordRequestDTO requestDto) {

		System.out.println("🔹 Password: " + requestDto.getPassword());
		System.out.println("🔹 Password Confirm: " + requestDto.getPasswordConfirm());

		//비밀번호 확인 일치 여부 검증
		if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		//사용자 확인
		SignupName user = signupNameRepository.findById(requestDto.getGeneralId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		//기존 비밀번호가 있는 경우 최신 비밀번호 가져오기
		List<Password> userPasswords = passwordRepository.findByUser(user);
		Password password = userPasswords.stream()
			.max((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt())) //최신 비밀번호 찾기
			.orElse(new Password()); //기존 비밀번호 없으면 새 객체 생성

		//비밀번호 암호화 후 저장
		password.setUser(user);
		password.setHashedPassword(passwordEncoder.encode(requestDto.getPassword()));
		passwordRepository.save(password);

		return new GeneralPasswordResponseDTO("SUCCESS", "비밀번호가 설정되었습니다.",
			new GeneralPasswordResponseDTO.Data(user.getGeneralId()));
	}
}
