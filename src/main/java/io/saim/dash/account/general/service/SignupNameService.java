package io.saim.dash.account.general.service;

import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.SignupNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SignupNameService {

	@Autowired
	private SignupNameRepository signupNameRepository;

	public SignupName registerUser(String name) {
		SignupName user = new SignupName();
		user.setGeneralName(name);
		user.setJoinedAt(LocalDateTime.now());	//임시 데이터 (전화번호, 이메일 등)
		user.setGeneralPhone("01012345678");
		user.setGeneralEmail(name + "@example.com");
		user.setVendorGroupId(1L);  //임시 벤더그룹 ID
		user.setDepartmentId(1L);  //임시 소속 ID

		return signupNameRepository.save(user);
	}
}
