package io.saim.dash.account.general.service;

import io.saim.dash.account.general.dto.GeneralPasswordRequestDTO;
import io.saim.dash.account.general.dto.GeneralPasswordResponseDTO;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupPasswordService {

	private final SignupNameRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public GeneralPasswordResponseDTO setPassword(GeneralPasswordRequestDTO requestDto) {
		//비밀번호 확인 일치 여부 검증
		if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		//사용자 확인 (없으면 예외 발생)
		SignupName user = signupNameRepository.findById(requestDto.getGeneralId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		//비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

		//비밀번호 저장
		Password password = new Password();
		password.setUser(user);
		password.setHashedPassword(encodedPassword);
		passwordRepository.save(password);

		//응답 반환
		return new GeneralPasswordResponseDTO("SUCCESS", "비밀번호가 설정되었습니다.",
			new GeneralPasswordResponseDTO.Data(user.getGeneralId()));
	}
}
