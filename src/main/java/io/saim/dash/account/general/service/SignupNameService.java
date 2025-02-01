package io.saim.dash.account.general.service;

import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.SignupNameRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SignupNameService {

	private final SignupNameRepository signupNameRepository;

	public SignupName registerUser(String name) {
		String phone = generateUniquePhone();

		SignupName user = new SignupName();
		user.setGeneralName(name);
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
