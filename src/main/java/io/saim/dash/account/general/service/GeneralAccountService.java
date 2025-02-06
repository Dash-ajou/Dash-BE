package io.saim.dash.account.general.service;

import java.time.LocalDateTime;

import io.saim.dash.account.auth.service.PhoneVerificationService;
import io.saim.dash.account.general.dto.GeneralAccountResponseDTO;
import io.saim.dash.account.general.dto.GeneralEmailVerifyConfirmDTO;
import io.saim.dash.account.general.dto.GeneralPhoneUpdateDTO;
import io.saim.dash.account.general.model.EmailVerification;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.EmailVerifyRepository;
import io.saim.dash.account.general.repository.SignupNameRepository;
import io.saim.dash.account.auth.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneralAccountService {

	private final SignupNameRepository signupNameRepository;
	private final SessionManager sessionManager;
	private final PhoneVerificationService phoneVerificationService;
	private final EmailVerifyService emailVerificationService;
	private final EmailVerifyRepository emailVerifyRepository;

	public GeneralAccountResponseDTO getGeneralAccountDetails(String sessionId) {
		//세션 ID를 통해 사용자 ID 조회
		String userId = sessionManager.getUserIdFromSession(sessionId);
		if (userId == null) {
			throw new IllegalArgumentException("유효하지 않은 세션입니다.");
		}

		//사용자 정보 조회
		SignupName user = signupNameRepository.findById(Long.parseLong(userId))
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		//이메일이 없을 경우 "등록된 이메일이 없습니다."로 설정
		String email = (user.getGeneralEmail() == null || user.getGeneralEmail().isBlank())
			? "등록된 이메일이 없습니다." : user.getGeneralEmail();

		return new GeneralAccountResponseDTO("SUCCESS", "계정 상세 정보를 성공적으로 가져왔습니다.",
			new GeneralAccountResponseDTO.Data(user.getGeneralName(), email, user.getGeneralPhone()));
	}

	@Transactional
	public boolean updatePhoneNumber(String sessionId, GeneralPhoneUpdateDTO updateDTO) {
		//세션에서 사용자 찾기
		String userId = sessionManager.getUserIdFromSession(sessionId);
		if (userId == null) {
			throw new IllegalArgumentException("유효하지 않은 세션입니다.");
		}

		SignupName user = signupNameRepository.findById(Long.parseLong(userId))
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		String newPhone = updateDTO.getGeneralNewPhone();
		String verifyCode = updateDTO.getGeneralVerifyCode();

		//새로운 전화번호 인증 확인
		boolean isVerified = phoneVerificationService.verifyCode(newPhone, verifyCode);
		if (!isVerified) {
			return false; //인증 실패 시 변경하지 않음
		}

		//전화번호 업데이트
		user.setGeneralPhone(newPhone);
		signupNameRepository.save(user);
		return true;
	}

	public boolean requestEmailVerification(String sessionId, String newEmail) {
		//세션 ID를 통해 사용자 찾기
		String userId = sessionManager.getUserIdFromSession(sessionId);
		if (userId == null) {
			throw new IllegalArgumentException("유효하지 않은 세션입니다.");
		}

		//기존 사용자 정보 가져오기
		SignupName user = signupNameRepository.findById(Long.parseLong(userId))
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		//이메일 인증 요청 수행
		return emailVerificationService.sendVerificationCode(newEmail);
	}

	@Transactional
	public boolean confirmEmailVerification(String sessionId, GeneralEmailVerifyConfirmDTO confirmDTO) {
		//세션 ID를 통해 사용자 ID 조회
		String userId = sessionManager.getUserIdFromSession(sessionId);
		if (userId == null) {
			throw new IllegalArgumentException("유효하지 않은 세션입니다.");
		}

		//사용자 정보 조회
		SignupName user = signupNameRepository.findById(Long.parseLong(userId))
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		//인증 요청된 이메일 데이터 조회
		EmailVerification verification = emailVerifyRepository.findByEmail(confirmDTO.getNewEmail())
			.orElseThrow(() -> new IllegalArgumentException("이메일 인증 요청을 찾을 수 없습니다."));

		//인증 코드 검증
		if (!verification.getVerificationCode().equals(confirmDTO.getEmailVerifyCode())) {
			return false;
		}

		//인증 코드 만료 여부 확인
		if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
			return false;
		}

		//이메일 업데이트
		user.setGeneralEmail(confirmDTO.getNewEmail());
		signupNameRepository.save(user);

		// 인증 정보 삭제 (사용 완료된 인증 코드)
		emailVerifyRepository.delete(verification);

		return true;
	}
}
