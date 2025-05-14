package io.saim.dash.account.general.service;

import io.saim.dash.account.general.model.EmailVerification;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.EmailVerifyRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {

	private final EmailVerifyRepository emailVerificationRepository;
	private final SignupNameRepository signupNameRepository;

	//인증 코드 생성 및 이메일 전송
	public boolean sendVerificationCode(String email) {
		String verificationCode = generateCode();
		LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(3);

		//기존 이메일 인증 정보가 있으면 삭제
		emailVerificationRepository.findByEmail(email).ifPresent(emailVerificationRepository::delete);

		//새로운 인증 정보 저장
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setEmail(email);
		emailVerification.setVerificationCode(verificationCode);
		emailVerification.setExpiresAt(expirationTime);

		emailVerificationRepository.save(emailVerification);

		//실제 이메일 발송 로직 (추후 구현 가능)
		System.out.println("인증 코드: " + verificationCode + " (이메일: " + email + ")");

		return true;
	}

	//이메일 변경을 위한 인증 요청
	@Transactional
	public boolean requestEmailVerification(GeneralUser user, String newEmail) {
		if (user == null) {
			throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
		}

		// 새로운 이메일로 인증 요청
		return sendVerificationCode(newEmail);
	}
	private String generateCode() {
		Random random = new Random();
		int code = 100000 + random.nextInt(900000); // 100000 ~ 999999 범위의 숫자
		return String.valueOf(code);
	}
}
