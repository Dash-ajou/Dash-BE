package io.saim.dash.account.general.service;

import io.saim.dash.account.general.dto.SignupCompleteRequestDTO;
import io.saim.dash.account.general.dto.SignupCompleteResponseDTO;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.SignupNameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupCompleteService {

	private final SignupNameRepository signupNameRepository;

	@Transactional
	public SignupCompleteResponseDTO completeSignup(SignupCompleteRequestDTO requestDTO) {
		SignupName user = null;

		//general_id로 기존 사용자 조회 (없으면 예외 발생)
		if (requestDTO.getGeneralId() != null && !requestDTO.getGeneralId().isBlank()) {
			user = signupNameRepository.findById(Long.parseLong(requestDTO.getGeneralId()))
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		} else {
			throw new IllegalArgumentException("general_id가 필요합니다.");
		}

		//DTO 데이터를 엔티티에 매핑 (널 값 방지)
		if (requestDTO.getUserType() != null) user.setGeneralType(requestDTO.getUserType());
		if (requestDTO.getGeneralName() != null) user.setGeneralName(requestDTO.getGeneralName());
		if (requestDTO.getGeneralPhone() != null) user.setGeneralPhone(requestDTO.getGeneralPhone());
		if (requestDTO.getGeneralEmail() != null) user.setGeneralEmail(requestDTO.getGeneralEmail());
		if (requestDTO.getVendorGroupId() != null) user.setVendorGroupId(requestDTO.getVendorGroupId());
		if (requestDTO.getDepartmentId() != null) user.setDepartmentId(requestDTO.getDepartmentId());

		//가입일 자동 설정 (기존 값이 없을 때만)
		if (user.getJoinedAt() == null) {
			user.setJoinedAt(LocalDateTime.now());
		}

		SignupName savedUser = signupNameRepository.save(user);

		return new SignupCompleteResponseDTO(
			"SUCCESS",
			"회원가입이 완료되었습니다.",
			new SignupCompleteResponseDTO.Data(
				savedUser.getGeneralId().toString(),
				savedUser.getGeneralType(),
				savedUser.getGeneralName(),
				savedUser.getGeneralPhone(),
				savedUser.getGeneralEmail(),
				savedUser.getJoinedAt().toString(),
				savedUser.getVendorGroupId(),
				savedUser.getDepartmentId()
			)
		);
	}
}
