package io.saim.dash.account.general.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
	private SignupName user;  //FK - user 테이블과 연결

	@Column(nullable = false)
	private String hashedPassword;  //암호화된 비밀번호 저장

	@CreationTimestamp
	private LocalDateTime createdAt;  //등록 시간 자동 생성
}
