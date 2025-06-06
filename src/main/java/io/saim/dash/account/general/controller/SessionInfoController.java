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
		Object user = session.getAttribute("user");
		String userType = (String) session.getAttribute("user_type");

		if (user == null || userType == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"사용자가 없습니다.",
				null
			));
		}

		Map<String, Object> userInfo = new HashMap<>();

		if ("GENERAL".equalsIgnoreCase(userType) && user instanceof GeneralUser generalUser) {
			String email = generalUser.getOwnerEmail();
			if (email != null && email.endsWith("@dummy.com")) {
				email = null;
			}

			userInfo.put("userType", "GENERAL");
			userInfo.put("name", generalUser.getOwnerName());
			userInfo.put("phone", generalUser.getOwnerPhone());
			userInfo.put("email", email);

		} else if ("PARTNER".equalsIgnoreCase(userType) && user instanceof PartnerUser partnerUser) {
			String email = partnerUser.getEmail();
			if (email != null && email.endsWith("@dummy.com")) {
				email = null;
			}

			userInfo.put("userType", "PARTNER");
			userInfo.put("name", partnerUser.getOwnerName());
			userInfo.put("phone", partnerUser.getPhone());
			userInfo.put("email", email);

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonResponseDTO<>(
				new VersionResponseDTO("1.0", "1.0"),
				APIStatus.FAILED,
				"사용자 정보가 올바르지 않습니다.",
				null
			));
		}

		return ResponseEntity.ok(new CommonResponseDTO<>(
			new VersionResponseDTO("1.0", "1.0"),
			APIStatus.SUCCESS,
			"사용자 정보 조회 성공",
			userInfo
		));
	}
}
