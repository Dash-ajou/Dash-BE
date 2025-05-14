package io.saim.dash.account.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "phone_verification")

public class PhoneVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가 ID
	private Long id;

	@Column(nullable = false, unique = true) //전화번호는 필수이며 중복 불가
	private String userPhone;

	@Column(nullable = false) //생성된 인증 코드
	private String userVerifyCode;

	@Column(nullable = false) //인증 코드 만료 시간
	private LocalDateTime expiresIn;

	@Column(nullable = false)
	private boolean userVerified;

	@Column(nullable = false)
	private String requestTime;

	public PhoneVerification() { //기본 생성자
	}

}
