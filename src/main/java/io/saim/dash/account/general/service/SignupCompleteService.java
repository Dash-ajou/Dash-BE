package io.saim.dash.account.general.service;

import io.saim.dash.account.general.dto.SignupCompleteRequestDTO;
import io.saim.dash.account.general.dto.SignupCompleteResponseDTO;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.SignupNameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupCompleteService {

	private final SignupNameRepository signupNameRepository;

	@Transactional
	public SignupCompleteResponseDTO completeSignup(SignupCompleteRequestDTO requestDTO) {
		// 기존 유저 확인 (일치하는 general_id가 있으면 업데이트)
		SignupName user;
		if (requestDTO.getGeneralId() != null && !requestDTO.getGeneralId().isBlank()) {
			user = signupNameRepository.findByGeneralId(Long.parseLong(requestDTO.getGeneralId()))
				.orElse(new SignupName()); // 기존 유저 없으면 새로 생성
		} else {
			user = new SignupName();
		}

		// DTO 데이터를 엔티티에 매핑 (널 값 방지)
		if (requestDTO.getUserType() != null) user.setGeneralType(requestDTO.getUserType());
		if (requestDTO.getGeneralName() != null) user.setGeneralName(requestDTO.getGeneralName());
		if (requestDTO.getGeneralPhone() != null) user.setGeneralPhone(requestDTO.getGeneralPhone());
		if (requestDTO.getGeneralEmail() != null) user.setGeneralEmail(requestDTO.getGeneralEmail());
		if (requestDTO.getVendorGroupId() != null) user.setVendorGroupId(requestDTO.getVendorGroupId());
		if (requestDTO.getDepartmentId() != null) user.setDepartmentId(requestDTO.getDepartmentId());

		// 가입일 자동 설정 (기존 값이 없을 때만)
		if (user.getJoinedAt() == null) {
			user.setJoinedAt(LocalDateTime.now());
		}

		SignupName savedUser = signupNameRepository.save(user);

		// 응답 반환
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
