package io.saim.dash.account.general.service;

import io.saim.dash.account.general.dto.SignupCompleteResponseDTO;
import io.saim.dash.account.general.dto.TestDTO;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnifiedSignupService {

	private final GeneralUserRepository signupNameRepository;
	private final GeneralPasswordRepository passwordRepository;
	private final PartnerUserRepository partnerUserRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public SignupCompleteResponseDTO signup(UnifiedSignupRequestDTO dto) {
		if (dto.getUserType() == null || dto.getUserType().isBlank()) {
			if (dto.getOwnerPhone() != null && !dto.getOwnerPhone().isBlank()) {
				dto.setUserType("PARTNER");
			} else if (dto.getGeneralPhone() != null && !dto.getGeneralPhone().isBlank()) {
				dto.setUserType("GENERAL");
			} else {
				throw new IllegalArgumentException("user_type을 유추할 수 없습니다. 전화번호 정보가 부족합니다.");
			}
		}

		if (dto.getPassword() == null || dto.getPasswordConfirm() == null ||
			dto.getPassword().isBlank() || dto.getPasswordConfirm().isBlank()) {
			throw new IllegalArgumentException("비밀번호와 비밀번호 확인은 모두 필수입니다.");
		}
		if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		String encodedPassword = passwordEncoder.encode(dto.getPassword().trim());

		if ("GENERAL".equalsIgnoreCase(dto.getUserType())) {
			//파트너 사용자와 전화번호 중복 방지
			if (partnerUserRepository.findByPhone(dto.getGeneralPhone()).isPresent()) {
				throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
			}

			String safeName = dto.getGeneralName() != null && !dto.getGeneralName().isBlank()
				? dto.getGeneralName().trim() : "이름없음";

			String safePhone = dto.getGeneralPhone() != null && !dto.getGeneralPhone().isBlank()
				? dto.getGeneralPhone().trim() : "00000000000";

			String safeEmail = dto.getGeneralEmail() != null && !dto.getGeneralEmail().isBlank()
				? dto.getGeneralEmail().trim() : UUID.randomUUID().toString().substring(0, 8) + "@dummy.com";

			GeneralUser user = GeneralUser.builder()
				.ownerName(safeName)
				.name(safeName)
				.ownerPhone(safePhone)
				.phone(safePhone)
				.ownerEmail(safeEmail)
				.email(safeEmail)
				.vendorGroupId(1L)
				.departmentId(1L)
				.type(dto.getUserType())
				.joinedAt(LocalDateTime.now())
				.password(encodedPassword)
				.build();

			user = signupNameRepository.save(user);

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

	public String test(TestDTO dto) {
		return passwordEncoder.encode(dto.getPassword().trim());
	}
}
