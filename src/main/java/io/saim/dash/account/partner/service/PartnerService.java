package io.saim.dash.account.partner.service;

import io.saim.dash.account.partner.dto.PartnerSignupRequestDTO;
import io.saim.dash.account.partner.dto.PartnerSignupResponseDTO;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PartnerService {

	private final PartnerUserRepository partnerRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public CommonResponseDTO<PartnerSignupResponseDTO> registerPartner(PartnerSignupRequestDTO requestDTO) {

		// 1. 이메일 중복 체크
		if (partnerRepository.findByEmail(requestDTO.getOwnerEmail()).isPresent()) {
			return new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"이미 가입된 이메일입니다.",
				null
			);
		}

		// 2. 비밀번호 확인
		if (!requestDTO.getPassword().equals(requestDTO.getPasswordConfirm())) {
			return new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"비밀번호와 비밀번호 확인이 일치하지 않습니다.",
				null
			);
		}

		// 3. 비밀번호 해시 처리
		String hashedPassword = passwordEncoder.encode(requestDTO.getPassword());

		// 4. 저장
		PartnerUser partner = PartnerUser.builder()
			.partnerName(requestDTO.getPartnerName())
			.partnerAddress(requestDTO.getPartnerAddress())
			.name(requestDTO.getOwnerName())
			.phone(requestDTO.getOwnerPhone())
			.email(requestDTO.getOwnerEmail())
			.isTemporary(requestDTO.isTemporary())
			.temporaryRegisterDate(requestDTO.getTemporaryRegisterDate())
			.password(hashedPassword)
			.build();

		PartnerUser savedPartner = partnerRepository.save(partner);

		return new CommonResponseDTO<>(
			new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"파트너 정보가 성공적으로 저장되었습니다.",
			new PartnerSignupResponseDTO(
				savedPartner.getId(),
				savedPartner.getPartnerName(),
				savedPartner.getPartnerAddress(),
				savedPartner.isTemporary(),
				savedPartner.getTemporaryRegisterDate() != null ?
					savedPartner.getTemporaryRegisterDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
					: null
			)
		);
	}
}
