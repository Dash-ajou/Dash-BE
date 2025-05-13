package io.saim.dash.account.partner.service;

import io.saim.dash.account.general.dto.GeneralPasswordRequestDTO;
import io.saim.dash.account.general.dto.GeneralPasswordResponseDTO;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartnerPasswordService {

	private final PartnerUserRepository partnerUserRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public CommonResponseDTO<GeneralPasswordResponseDTO> setPassword(GeneralPasswordRequestDTO requestDto) {

		if (requestDto.getPartnerId() == null) {
			throw new IllegalArgumentException("파트너 ID는 필수입니다.");
		}

		// 파트너 존재 여부 확인
		PartnerUser partnerUser = partnerUserRepository.findById(requestDto.getPartnerId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파트너입니다."));

		// 비밀번호 일치 여부 확인
		if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		// 비밀번호 암호화 후 저장
		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
		partnerUser.setPassword(encodedPassword);
		partnerUserRepository.save(partnerUser);

		return new CommonResponseDTO<>(
			new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"비밀번호가 설정되었습니다.",
			new GeneralPasswordResponseDTO("SUCCESS", "비밀번호가 설정되었습니다.", partnerUser.getPartnerId().toString())
		);
	}
}
