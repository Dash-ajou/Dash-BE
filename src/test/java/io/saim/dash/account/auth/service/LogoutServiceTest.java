package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.session.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogoutServiceTest {

	private SessionManager sessionManager;
	private LogoutService logoutService;

	@BeforeEach
	void setUp() {
		//세션 매니저를 Mock 객체로 생성
		sessionManager = mock(SessionManager.class);
		//Mock된 세션 매니저를 사용하여 LogoutService 초기화
		logoutService = new LogoutService(sessionManager);
	}

	@Test
	void testInvalidateSession_Success() {
		String sessionId = "valid-session-id";

		//유효한 세션 ID가 존재한다고 가정
		when(sessionManager.isValidSession(sessionId)).thenReturn(true);

		//로그아웃 실행
		boolean result = logoutService.invalidateSession(sessionId);

		//로그아웃이 성공해야 함
		assertTrue(result);
		//세션이 정확히 1번 삭제되었는지 확인
		verify(sessionManager, times(1)).invalidateSession(sessionId);
	}

	@Test
	void testInvalidateSession_Failure() {
		String sessionId = "invalid-session-id";

		//유효하지 않은 세션 ID라고 가정
		when(sessionManager.isValidSession(sessionId)).thenReturn(false);

		//로그아웃 실행
		boolean result = logoutService.invalidateSession(sessionId);

		//로그아웃이 실패해야 함
		assertFalse(result);
		//세션 삭제 메서드가 호출되지 않았는지 확인
		verify(sessionManager, never()).invalidateSession(sessionId);
	}
}
