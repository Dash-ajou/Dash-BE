package io.saim.dash.account.partner.controller;

import io.saim.dash.account.partner.dto.PartnerMyPageResponseDTO;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.service.PartnerMyPageService;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerMyPageController {

	private final PartnerMyPageService partnerMyPageService;
	private final PartnerUserRepository partnerRepository;

	@GetMapping("/mypage")
	public ResponseEntity<CommonResponseDTO<PartnerMyPageResponseDTO>> getPartnerMyPage(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null || userDetails.getUsername() == null) {
			return ResponseEntity.status(401).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"인증되지 않은 사용자입니다. 로그인 후 다시 시도해주세요.",
				null
			));
		}

		//PartnerUser 찾기 (전화번호를 기준으로 조회)
		Optional<PartnerUser> partnerUserOpt = partnerRepository.findByOwnerPhone(userDetails.getUsername());

		if (partnerUserOpt.isEmpty()) {
			return ResponseEntity.status(403).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"파트너 계정을 찾을 수 없습니다.",
				null
			));
		}

		try {
			PartnerMyPageResponseDTO response = partnerMyPageService.getPartnerMyPage(partnerUserOpt.get());
			return ResponseEntity.ok(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.SUCCESS,
				"사용자 정보 및 메뉴 데이터를 성공적으로 가져왔습니다.",
				response
			));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILURE,
				"서버 내부 오류가 발생했습니다.",
				null
			));
		}
	}
}
