package io.saim.dash.account.general.service;

import io.saim.dash.account.auth.session.SessionManager;  // 🔥 auth 패키지의 SessionManager 사용
import io.saim.dash.account.general.dto.MyPageResponseDTO;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

	private final SignupNameRepository signupNameRepository;
	private final SessionManager sessionManager;

	public MyPageResponseDTO getMyPageInfo(String sessionId) {
		//세션 유효성 검사
		if (!sessionManager.isValidSession(sessionId)) {
			throw new IllegalArgumentException("유효하지 않은 세션 ID입니다.");
		}

		//세션에서 사용자 ID 가져오기
		String userId = sessionManager.getUserIdFromSession(sessionId);
		SignupName user = signupNameRepository.findById(Long.parseLong(userId))
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		return new MyPageResponseDTO(
			"SUCCESS",
			"계정 정보를 성공적으로 가져왔습니다.",
			new MyPageResponseDTO.Data(
				user.getGeneralName(),
				new MyPageResponseDTO.CouponStatus(1, 0), //예제 값, DB에서 가져오기
				new MyPageResponseDTO.Menus()
			)
		);
	}
}
