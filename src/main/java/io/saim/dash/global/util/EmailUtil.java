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
		message.setSubject("DASH 이메일 인증 코드");
		message.setText("아래 코드를 입력해주세요:\n\n" + code + "\n\n(유효시간: 3분)");

		mailSender.send(message);
	}
}
