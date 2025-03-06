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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartnerService {

	private final PartnerUserRepository partnerRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public CommonResponseDTO<PartnerSignupResponseDTO> registerPartner(PartnerSignupRequestDTO requestDTO) {

		//기존 이메일이 존재하는지 확인
		if (partnerRepository.findByOwnerEmail(requestDTO.getOwnerEmail()).isPresent()) {
			return new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"이미 가입된 이메일입니다.",
				null
			);
		}

		//비밀번호 자동 생성 (초기 랜덤 비밀번호 설정)
		String randomPassword = UUID.randomUUID().toString().substring(0, 12); // 12자리 랜덤 비밀번호 생성
		String hashedPassword = passwordEncoder.encode(randomPassword); // 🔹 비밀번호 해싱

		PartnerUser partner = PartnerUser.builder()
			.partnerName(requestDTO.getPartnerName())
			.partnerAddress(requestDTO.getPartnerAddress())
			.ownerName(requestDTO.getOwnerName())
			.ownerPhone(requestDTO.getOwnerPhone())
			.ownerEmail(requestDTO.getOwnerEmail())
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
				savedPartner.getPartnerId(),
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
