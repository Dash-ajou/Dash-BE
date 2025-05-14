package io.saim.dash.account.general.service;

import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.account.general.dto.GeneralPasswordRequestDTO;
import io.saim.dash.account.general.dto.GeneralPasswordResponseDTO;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupPasswordService {

	private final SignupNameRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public CommonResponseDTO<GeneralPasswordResponseDTO> setPassword(GeneralPasswordRequestDTO requestDto) {

		// 비밀번호 확인 일치 여부 검증
		if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		// 사용자 확인
		GeneralUser user = signupNameRepository.findById(requestDto.getGeneralId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		// 기존 비밀번호 가져오기 (최신 비밀번호 찾기)
		Optional<Password> existingPasswordOpt = passwordRepository.findByUser(user)
			.stream()
			.max((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()));

		// 새로운 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

		// 기존 비밀번호가 있으면 업데이트, 없으면 새로 추가
		if (existingPasswordOpt.isPresent()) {
			Password existingPassword = existingPasswordOpt.get();
			System.out.println("기존 비밀번호 업데이트: " + existingPassword.getPasswordId());
			existingPassword.setHashedPassword(encodedPassword);
			existingPassword.setCreatedAt(LocalDateTime.now());
			passwordRepository.save(existingPassword);
		} else {
			System.out.println("새로운 비밀번호 생성");
			Password newPassword = new Password();
			newPassword.setUser(user);
			newPassword.setHashedPassword(encodedPassword);
			newPassword.setCreatedAt(LocalDateTime.now());
			passwordRepository.save(newPassword);
		}

		return new CommonResponseDTO<>(
			new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"비밀번호가 설정되었습니다.",
			new GeneralPasswordResponseDTO("SUCCESS", "비밀번호가 설정되었습니다.", user.getGeneralId().toString())
		);
	}
}
