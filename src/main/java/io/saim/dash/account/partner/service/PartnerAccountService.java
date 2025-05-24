package io.saim.dash.account.partner.service;

import io.saim.dash.account.auth.service.PhoneVerificationService;
import io.saim.dash.account.partner.dto.PartnerAccountResponseDTO;
import io.saim.dash.account.partner.dto.PartnerPhoneUpdateDTO;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerAccountService {

	private final PartnerUserRepository partnerUserRepository;
	private final PhoneVerificationService phoneVerificationService;

	public CommonResponseDTO<PartnerAccountResponseDTO> getPartnerAccountDetails(String ownerPhone) {
		Optional<PartnerUser> partnerOpt = partnerUserRepository.findByPhone(ownerPhone);

		if (partnerOpt.isEmpty()) {
			return new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"파트너 계정을 찾을 수 없습니다.",
				null
			);
		}

		PartnerUser partner = partnerOpt.get();
		PartnerAccountResponseDTO responseDTO = new PartnerAccountResponseDTO(
			partner.getOwnerName() != null ? partner.getOwnerName() : "정보 없음",
			partner.getPhone(),
			partner.getEmail() != null ? partner.getEmail() : "정보 없음",
			partner.isTemporary() ? "임시가입" : "정식가입",
			partner.getTemporaryRegisterDate() != null
				? partner.getTemporaryRegisterDate().toString()
				: "N/A"
		);

		return new CommonResponseDTO<>(
			new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"사용자 정보를 성공적으로 가져왔습니다.",
			responseDTO
		);
	}

	@Transactional
	public boolean updatePhoneNumber(PartnerUser partnerUser, PartnerPhoneUpdateDTO updateDTO) {
		//전화번호 인증 코드 검증 (기존 '/auth/phone/verify' 사용)
		boolean isVerified = phoneVerificationService.verifyCode(updateDTO.getOwnerNewPhone(), updateDTO.getOwnerVerifyCode());
		if (!isVerified) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 코드입니다.");
		}

		//전화번호 변경
		partnerUser.setPhone(updateDTO.getOwnerNewPhone());
		partnerUserRepository.save(partnerUser);

		return true;
	}

	@Transactional
	public boolean deleteAccount(PartnerUser partnerUser) {
		if (partnerUser == null) {
			return false;
		}

		try {
			partnerUserRepository.delete(partnerUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
