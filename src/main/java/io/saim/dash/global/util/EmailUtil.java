package io.saim.dash.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {

	private final JavaMailSender mailSender;

	public void sendVerificationEmail(String toEmail, String code) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("[DASH] 이메일 인증 코드");
		message.setText("인증 코드: " + code + "\n\n" + "해당 코드는 3분간 유효합니다.");
		mailSender.send(message);
	}
}
