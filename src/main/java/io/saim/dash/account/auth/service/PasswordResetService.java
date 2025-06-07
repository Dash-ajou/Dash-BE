package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.dto.PasswordResetRequestDTO;
import io.saim.dash.account.auth.dto.PasswordResetResponseDTO;
import io.saim.dash.account.auth.model.PhoneVerification;
import io.saim.dash.account.auth.repository.PhoneVerificationRepository;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

	private final GeneralUserRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final PhoneVerificationRepository phoneVerificationRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final PartnerUserRepository partnerUserRepository;

	@Transactional
	public PasswordResetResponseDTO resetPassword(PasswordResetRequestDTO requestDTO) {
		// 1. 전화번호 인증 확인
		PhoneVerification verification = phoneVerificationRepository.findByUserPhone(requestDTO.getUserPhone())
			.orElseThrow(() -> new IllegalArgumentException("해당 전화번호의 인증 요청이 없습니다."));

		if (!verification.isUserVerified()) {
			throw new IllegalArgumentException("전화번호 인증이 완료되지 않았습니다.");
		}

		// 2. 비밀번호 일치 여부 확인
		if (!requestDTO.getNewPassword().equals(requestDTO.getNewPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		// 3. 일반 사용자 또는 파트너 사용자 조회
		Optional<GeneralUser> generalUserOpt = signupNameRepository.findByPhone(requestDTO.getUserPhone());
		Optional<PartnerUser> partnerUserOpt = partnerUserRepository.findByPhone(requestDTO.getUserPhone());

		if (generalUserOpt.isEmpty() && partnerUserOpt.isEmpty()) {
			throw new IllegalArgumentException("등록되지 않은 전화번호입니다.");
		}

		// 4. 비밀번호 재설정
		if (generalUserOpt.isPresent()) {
			GeneralUser user = generalUserOpt.get();

			List<Password> userPasswords = passwordRepository.findByUser(user);
			Password password = userPasswords.stream()
				.max((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()))
				.orElse(new Password());

			password.setUser(user);
			password.changePassword(requestDTO.getNewPassword(), passwordEncoder);
			passwordRepository.save(password);
		} else {
			PartnerUser partner = partnerUserOpt.get();
			partner.changePassword(requestDTO.getNewPassword(), passwordEncoder);
			partnerUserRepository.save(partner);
		}

		return new PasswordResetResponseDTO("SUCCESS", "비밀번호가 성공적으로 재설정되었습니다.");
	}
}
