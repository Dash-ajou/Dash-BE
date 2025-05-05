package io.saim.dash.account.general.controller;

import io.saim.dash.account.general.dto.GeneralPasswordRequestDTO;
import io.saim.dash.account.general.dto.GeneralPasswordResponseDTO;
import io.saim.dash.account.general.service.SignupPasswordService;
import io.saim.dash.account.partner.service.PartnerPasswordService;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.APIStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup/password")
@RequiredArgsConstructor
public class SignupPasswordController {

	private final SignupPasswordService userService;
	private final PartnerPasswordService partnerService;

	@PostMapping
	public ResponseEntity<CommonResponseDTO<GeneralPasswordResponseDTO>> setPassword(
		@Valid @RequestBody GeneralPasswordRequestDTO requestDto) {

		if (requestDto.isGeneralUser() && requestDto.isPartnerUser()) {
			return ResponseEntity.badRequest().body(new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"일반 사용자 ID와 파트너 ID 중 하나만 입력해야 합니다.",
				null
			));
		}

		if (!requestDto.isGeneralUser() && !requestDto.isPartnerUser()) {
			return ResponseEntity.badRequest().body(new CommonResponseDTO<>(
				new CommonResponseDTO.VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"사용자 ID 또는 파트너 ID는 필수입니다.",
				null
			));
		}

		CommonResponseDTO<GeneralPasswordResponseDTO> response;

		if (requestDto.isGeneralUser()) {
			response = userService.setPassword(requestDto);
		} else {
			response = partnerService.setPassword(requestDto);
		}

		return ResponseEntity.status(201).body(response);
	}
}
