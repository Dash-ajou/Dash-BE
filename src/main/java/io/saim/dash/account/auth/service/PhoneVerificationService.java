package io.saim.dash.account.auth.service;

import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.saim.dash.account.auth.model.PhoneVerification;
import io.saim.dash.account.auth.repository.PhoneVerificationRepository;
import io.saim.dash.global.util.SmsService;

@Service
public class PhoneVerificationService {

	@Autowired
	private PhoneVerificationRepository phoneVerificationRepository;

	@Autowired
	private SmsService smsService;

	public String requestVerification(String userPhone) {
		if (userPhone == null || userPhone.isEmpty()) {
			throw new IllegalArgumentException("userPhone cannot be null or empty");
		}

		String userVerifyCode = String.valueOf(new Random().nextInt(900000) + 100000);
		LocalDateTime expiresIn = LocalDateTime.now().plusMinutes(5);

		phoneVerificationRepository.findByUserPhone(userPhone)
			.ifPresent(phoneVerificationRepository::delete);

		PhoneVerification verification = new PhoneVerification();
		verification.setUserPhone(userPhone);
		verification.setUserVerifyCode(userVerifyCode);
		verification.setExpiresIn(expiresIn);
		verification.setUserVerified(false);
		verification.setRequestTime(LocalDateTime.now().toString());

		phoneVerificationRepository.save(verification);

		String message = "[DASH] 인증번호는 " + userVerifyCode + "입니다.";
		smsService.sendSms(userPhone, message);

		return userVerifyCode;
	}

	public boolean verifyCode(String userPhone, String userVerifyCode) {
		return phoneVerificationRepository.findByUserPhone(userPhone)
			.filter(verification -> verification.getUserVerifyCode().equals(userVerifyCode) &&
				verification.getExpiresIn().isAfter(LocalDateTime.now()))
			.map(verification -> {
				verification.setUserVerified(true);
				phoneVerificationRepository.save(verification);
				return true;
			})
			.orElse(false);
	}
}
