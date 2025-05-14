package io.saim.dash.account.general.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "passwords")
public class Password {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long passwordId;  //PK

	@ManyToOne
	@JoinColumn(name = "general_id", nullable = false)
	private GeneralUser user;  //FK - user 테이블과 연결

	@Column(nullable = false)
	private String hashedPassword;  //암호화된 비밀번호 저장

	@CreationTimestamp
	private LocalDateTime createdAt;  //등록 시간 자동 생성

	//가장 최근 비밀번호 가져오는 정적 메서드 추가
	public static Password getLatestPassword(List<Password> passwords) {
		return passwords.stream()
			.max((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()))
			.orElse(null);
	}

	//비밀번호 변경 메서드 추가
	public void changePassword(String newPassword, BCryptPasswordEncoder encoder) {
		this.hashedPassword = encoder.encode(newPassword);
	}
}
