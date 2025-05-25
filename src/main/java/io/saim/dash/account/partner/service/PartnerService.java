package io.saim.dash.account.partner.service;

import io.saim.dash.account.general.repository.GeneralUserRepository;
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
	private final GeneralUserRepository generalUserRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public CommonResponseDTO<PartnerSignupResponseDTO> registerPartner(PartnerSignupRequestDTO requestDTO) {

		if (requestDTO.getOwnerPhone() == null || requestDTO.getOwnerPhone().isBlank()) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT.replaceArg("전화번호"));
		}

		//일반 사용자와 전화번호 중복 방지
		if (generalUserRepository.findByPhone(requestDTO.getOwnerPhone()).isPresent()) {
			throw new ServiceException(ServiceExceptionContent.ALREADY_REGISTERED.replaceArg("전화번호"));
		}

		PartnerUser existingPartner = partnerRepository.findByPhone(requestDTO.getOwnerPhone()).orElse(null);
		if (existingPartner != null && !existingPartner.isTemporary()) {
			throw new ServiceException(ServiceExceptionContent.ALREADY_REGISTERED.replaceArg("전화번호"));
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

		try {
			PartnerUser partner;

			if (existingPartner != null && existingPartner.isTemporary()) {
				existingPartner.setPartnerName(requestDTO.getPartnerName());
				existingPartner.setPartnerAddress(requestDTO.getPartnerAddress());
				existingPartner.setName(requestDTO.getOwnerName());
				existingPartner.setEmail(requestDTO.getOwnerEmail());
				existingPartner.setPassword(hashedPassword);
				existingPartner.setTemporary(false);
				existingPartner.setTemporaryRegisterDate(LocalDateTime.now());

				partner = partnerRepository.save(existingPartner);
			} else {
				partner = PartnerUser.builder()
					.partnerName(requestDTO.getPartnerName())
					.partnerAddress(requestDTO.getPartnerAddress())
					.name(requestDTO.getOwnerName())
					.phone(requestDTO.getOwnerPhone())
					.email(requestDTO.getOwnerEmail())
					.isTemporary(false)
					.temporaryRegisterDate(LocalDateTime.now())
					.password(hashedPassword)
					.build();

				partner = partnerRepository.save(partner);
			}

			return new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"파트너 정보가 성공적으로 저장되었습니다.",
				new PartnerSignupResponseDTO(
					partner.getId(),
					partner.getPartnerName(),
					partner.getPartnerAddress(),
					partner.isTemporary(),
					partner.getTemporaryRegisterDate() != null ?
						partner.getTemporaryRegisterDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
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
