package io.saim.dash.account.partner.service;

import io.saim.dash.account.partner.dto.PartnerSignupRequestDTO;
import io.saim.dash.account.partner.dto.PartnerSignupResponseDTO;
import io.saim.dash.account.partner.model.Partner;
import io.saim.dash.account.partner.repository.PartnerRepository;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PartnerService {

	private final PartnerRepository partnerRepository;

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

		Partner partner = Partner.builder()
			.partnerName(requestDTO.getPartnerName())
			.partnerAddress(requestDTO.getPartnerAddress())
			.ownerName(requestDTO.getOwnerName())
			.ownerPhone(requestDTO.getOwnerPhone())
			.ownerEmail(requestDTO.getOwnerEmail())
			.isTemporary(requestDTO.isTemporary())
			.temporaryRegisterDate(requestDTO.getTemporaryRegisterDate())
			.build();

		Partner savedPartner = partnerRepository.save(partner);

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
