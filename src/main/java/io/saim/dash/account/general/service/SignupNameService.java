package io.saim.dash.account.general.service;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.SignupNameRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SignupNameService {

	private final SignupNameRepository signupNameRepository;

	public GeneralUser registerUser(String name) {
		String phone = generateUniquePhone();

		GeneralUser user = new GeneralUser();
		user.setGeneralName(name);
		//비밀번호 기본값 설정 (추후 /signup/password에서 변경됨)
		user.setPassword(new BCryptPasswordEncoder().encode("DUMMY_PASSWORD"));
		user.setGeneralPhone(phone);
		user.setJoinedAt(LocalDateTime.now());

		return signupNameRepository.save(user);
	}

	//랜덤한 전화번호를 생성하고 중복 체크 후 반환
	private String generateUniquePhone() {
		Random random = new Random();
		String phone;

		do {
			phone = "010" + (random.nextInt(9000) + 1000) + (random.nextInt(9000) + 1000);
		} while (signupNameRepository.existsByGeneralPhone(phone));

		return phone;
	}
}
