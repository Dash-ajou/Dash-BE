package io.saim.dash.account.general.service;

import java.time.LocalDateTime;

import io.saim.dash.account.auth.service.PhoneVerificationService;
import io.saim.dash.account.general.dto.GeneralAccountResponseDTO;
import io.saim.dash.account.general.dto.GeneralEmailVerifyConfirmDTO;
import io.saim.dash.account.general.dto.GeneralPhoneUpdateDTO;
import io.saim.dash.account.general.model.EmailVerification;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.EmailVerifyRepository;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.global.dto.APIStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneralAccountService {

	private final GeneralUserRepository signupNameRepository;
	private final PhoneVerificationService phoneVerificationService;
	private final EmailVerifyService emailVerificationService;
	private final EmailVerifyRepository emailVerifyRepository;

	public GeneralAccountResponseDTO getGeneralAccountDetails(GeneralUser user) {
		if (user == null) {
			throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
		}

		String email = (user.getEmail() == null || user.getEmail().isBlank())
			? "등록된 이메일이 없습니다." : user.getEmail();

		return new GeneralAccountResponseDTO("1.0", "1.0", APIStatus.SUCCESS, "계정 상세 정보를 성공적으로 가져왔습니다.",
			new GeneralAccountResponseDTO.Data(user.getName(), email, user.getPhone()));
	}

	@Transactional
	public boolean updatePhoneNumber(GeneralUser user, GeneralPhoneUpdateDTO updateDTO) {
		if (user == null) {
			throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
		}

		String newPhone = updateDTO.getGeneralNewPhone();
		String verifyCode = updateDTO.getGeneralVerifyCode();

		boolean isVerified = phoneVerificationService.verifyCode(newPhone, verifyCode);
		if (!isVerified) {
			return false;
		}

		user.setPhone(newPhone);
		signupNameRepository.save(user);
		return true;
	}

	public boolean requestEmailVerification(GeneralUser user, String newEmail) {
		if (user == null) {
			throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
		}

		return emailVerificationService.sendVerificationCode(newEmail);
	}

	@Transactional
	public boolean confirmEmailVerification(GeneralUser user, GeneralEmailVerifyConfirmDTO confirmDTO) {
		if (user == null) {
			throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
		}

		EmailVerification verification = emailVerifyRepository.findByEmail(confirmDTO.getNewEmail())
			.orElseThrow(() -> new IllegalArgumentException("이메일 인증 요청을 찾을 수 없습니다."));

		if (!verification.getVerificationCode().equals(confirmDTO.getEmailVerifyCode())) {
			return false;
		}

		if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
			return false;
		}

		user.setEmail(confirmDTO.getNewEmail());
		user.setOwnerEmail(confirmDTO.getNewEmail());
		signupNameRepository.save(user);
		emailVerifyRepository.delete(verification);

		return true;
	}

	//회원 탈퇴 로직
	@Transactional
	public boolean deleteAccount(GeneralUser user) {
		if (user == null) {
			return false;
		}

		signupNameRepository.delete(user);
		return true;
	}
}
