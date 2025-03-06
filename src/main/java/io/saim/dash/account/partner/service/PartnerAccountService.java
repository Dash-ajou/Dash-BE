package io.saim.dash.account.partner.service;

import io.saim.dash.account.partner.dto.PartnerAccountResponseDTO;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerAccountService {

	private final PartnerUserRepository partnerUserRepository;

	public CommonResponseDTO<PartnerAccountResponseDTO> getPartnerAccountDetails(String ownerPhone) {
		Optional<PartnerUser> partnerOpt = partnerUserRepository.findByOwnerPhone(ownerPhone);

		if (partnerOpt.isEmpty()) {
			return new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"파트너 계정을 찾을 수 없습니다.",
				null
			);
		}

		PartnerUser partner = partnerOpt.get();
		PartnerAccountResponseDTO responseDTO = new PartnerAccountResponseDTO(
			partner.getOwnerName(),
			partner.getOwnerPhone(),
			partner.getOwnerEmail(),
			partner.isTemporary() ? "임시가입 여부" : "정식가입",
			partner.getTemporaryRegisterDate() != null ? partner.getTemporaryRegisterDate().toString() : "N/A"
		);

		return new CommonResponseDTO<>(
			new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"사용자 정보를 성공적으로 가져왔습니다.",
			responseDTO
		);
	}
}
