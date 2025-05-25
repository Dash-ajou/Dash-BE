package io.saim.dash.account.partner.service;

import io.saim.dash.account.partner.dto.PartnerSignupRequestDTO;
import io.saim.dash.account.partner.dto.PartnerSignupResponseDTO;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PartnerService {

	private final PartnerUserRepository partnerRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public CommonResponseDTO<PartnerSignupResponseDTO> registerPartner(PartnerSignupRequestDTO requestDTO) {

		if (requestDTO.getOwnerPhone() == null || requestDTO.getOwnerPhone().isBlank()) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT.replaceArg("전화번호"));
		}

		if (requestDTO.getOwnerEmail() != null && !requestDTO.getOwnerEmail().isBlank()) {
			if (partnerRepository.findByEmail(requestDTO.getOwnerEmail()).isPresent()) {
				throw new ServiceException(ServiceExceptionContent.ALREADY_REGISTERED.replaceArg("이메일"));
			}
		}

		//비밀번호 검증 및 기본값 처리
		if (requestDTO.getPassword() == null || requestDTO.getPassword().isBlank()) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT.replaceArg("비밀번호"));
		}
		if (requestDTO.getPasswordConfirm() == null || requestDTO.getPasswordConfirm().isBlank()) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT.replaceArg("비밀번호 확인"));
		}
		if (!requestDTO.getPassword().equals(requestDTO.getPasswordConfirm())) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT.replaceArg("비밀번호 확인"));
		}

		String hashedPassword = passwordEncoder.encode(requestDTO.getPassword());

		PartnerUser partner = PartnerUser.builder()
			.partnerName(requestDTO.getPartnerName())
			.partnerAddress(requestDTO.getPartnerAddress())
			.name(requestDTO.getOwnerName())
			.phone(requestDTO.getOwnerPhone())
			.email(requestDTO.getOwnerEmail())
			.isTemporary(requestDTO.isTemporary())
			.temporaryRegisterDate(requestDTO.getTemporaryRegisterDate() != null
				? requestDTO.getTemporaryRegisterDate()
				: LocalDateTime.now())
			.password(hashedPassword)
			.build();

		try {
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

		} catch (DataIntegrityViolationException e) {
			throw new ServiceException(ServiceExceptionContent.DUPLICATE_PHONE);
		} catch (Exception e) {
			throw new ServiceException(ServiceExceptionContent.INTERNAL_SERVER_ERROR);
		}
	}
}
