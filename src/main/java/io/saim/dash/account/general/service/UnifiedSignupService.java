package io.saim.dash.account.general.service;

import io.saim.dash.account.general.dto.SignupCompleteResponseDTO;
import io.saim.dash.account.general.dto.UnifiedSignupRequestDTO;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.model.Password;
import io.saim.dash.account.general.repository.GeneralPasswordRepository;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UnifiedSignupService {

	private final GeneralUserRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final PartnerUserRepository partnerUserRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public SignupCompleteResponseDTO signup(UnifiedSignupRequestDTO dto) {
		if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		String encodedPassword = passwordEncoder.encode(dto.getPassword().trim());

		if ("GENERAL".equalsIgnoreCase(dto.getUserType())) {
			GeneralUser user = GeneralUser.builder()
				.ownerName(dto.getGeneralName())
				.name(dto.getGeneralName())
				.ownerPhone(dto.getGeneralName())
				.phone(dto.getGeneralPhone())
				.ownerEmail(dto.getGeneralEmail())
				.email(dto.getGeneralEmail())
				.vendorGroupId(Long.parseLong(dto.getVendorGroupId()))
				.departmentId(Long.parseLong(dto.getDepartmentId()))
				.type(dto.getUserType())
				.joinedAt(LocalDateTime.now())
				.password(encodedPassword)
				.build();

			user = signupNameRepository.save(user); // ⚠️ 이때 joined_at이 null이면 에러남

			Password password = Password.builder()
				.user(user)
				.hashedPassword(encodedPassword)
				.build();
			passwordRepository.save(password);

			return new SignupCompleteResponseDTO(
				"SUCCESS",
				"회원가입이 완료되었습니다.",
				new SignupCompleteResponseDTO.Data(
					user.getId().toString(),
					user.getType(),
					user.getName(),
					user.getPhone(),
					user.getEmail(),
					user.getJoinedAt().toString(),
					user.getVendorGroupId(),
					user.getDepartmentId()
				)
			);
		} else if ("PARTNER".equalsIgnoreCase(dto.getUserType())) {
			//파트너 사용자 회원가입
			PartnerUser partner = PartnerUser.builder()
				.partnerName(dto.getPartnerName())
				.partnerAddress(dto.getPartnerAddress())
				.name(dto.getOwnerName())
				.phone(dto.getOwnerPhone())
				.email(dto.getOwnerEmail())
				.joinedAt(LocalDateTime.now())
				.isTemporary(false)
				.password(encodedPassword)
				.build();

			partner = partnerUserRepository.save(partner);

			return new SignupCompleteResponseDTO(
				"SUCCESS", "파트너 회원가입이 완료되었습니다.",
				new SignupCompleteResponseDTO.Data(
					partner.getId().toString(),
					dto.getUserType(),
					partner.getName(),
					partner.getPhone(),
					partner.getEmail(),
					partner.getJoinedAt().toString(),
					null, null
				)
			);
		} else {
			throw new IllegalArgumentException("지원하지 않는 user_type입니다.");
		}
	}
}
