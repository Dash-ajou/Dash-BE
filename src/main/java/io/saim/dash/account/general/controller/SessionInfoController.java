package io.saim.dash.account.general.controller;

import java.util.HashMap;
import java.util.Map;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class SessionInfoController {

	@GetMapping("/session-info")
	public ResponseEntity<CommonResponseDTO<Map<String, Object>>> getSessionUserInfo(HttpSession session) {
		String userType = (String) session.getAttribute("user_type");
		Object user = null; // ✅ 수정: user 객체는 이후 분기에서 꺼냄

		if (userType == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"사용자 타입이 없습니다.",
				null
			));
		}

		Map<String, Object> userInfo = new HashMap<>();

		if ("GENERAL".equalsIgnoreCase(userType)) {
			user = session.getAttribute("LOGIN_GENERAL_USER");
			if (user instanceof GeneralUser generalUser) {
				String email = generalUser.getOwnerEmail();
				if (email != null && email.endsWith("@dummy.com")) {
					email = null;
				}

				userInfo.put("userType", "GENERAL");
				userInfo.put("name", generalUser.getOwnerName());
				userInfo.put("phone", generalUser.getOwnerPhone());
				userInfo.put("email", email);
			} else {
				return unauthorized("일반 사용자 정보가 없습니다.");
			}

		} else if ("PARTNER".equalsIgnoreCase(userType)) {
			user = session.getAttribute("LOGIN_PARTNER_USER");
			if (user instanceof PartnerUser partnerUser) {
				String email = partnerUser.getEmail();
				if (email != null && email.endsWith("@dummy.com")) {
					email = null;
				}

				userInfo.put("userType", "PARTNER");
				userInfo.put("name", partnerUser.getOwnerName());
				userInfo.put("phone", partnerUser.getPhone());
				userInfo.put("email", email);
			} else {
				return unauthorized("파트너 사용자 정보가 없습니다.");
			}

		} else {
			return unauthorized("지원하지 않는 사용자 타입입니다.");
		}

		return ResponseEntity.ok(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"사용자 정보 조회 성공",
			userInfo
		));
	}

	private ResponseEntity<CommonResponseDTO<Map<String, Object>>> unauthorized(String message) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.FAILED,
			message,
			null
		));
	}
}
