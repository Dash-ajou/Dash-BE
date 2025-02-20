package io.saim.dash.account.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserVerifyCode() {
		return userVerifyCode;
	}

	public void setUserVerifyCode(String userVerifyCode) {
		this.userVerifyCode = userVerifyCode;
	}

	public LocalDateTime getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(LocalDateTime expiresIn) {
		this.expiresIn = expiresIn;
	}

	public boolean isUserVerified() {
		return userVerified;
	}

	public void setUserVerified(boolean userVerified) {
		this.userVerified = userVerified;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
}
