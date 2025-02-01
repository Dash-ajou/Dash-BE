package io.saim.dash.account.auth.session;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

	private final Map<String, String> sessionStore = new ConcurrentHashMap<>();

	public void createSession(String sessionId, String userId) {
		System.out.println("세션 생성: " + sessionId + " for user " + userId);
		sessionStore.put(sessionId, userId);
	}

	public boolean isValidSession(String sessionId) {
		System.out.println("세션 유효성 검사: " + sessionId + " 존재? " + sessionStore.containsKey(sessionId));
		return sessionStore.containsKey(sessionId);
	}

	public void invalidateSession(String sessionId) {
		System.out.println("세션 삭제: " + sessionId);
		sessionStore.remove(sessionId);
	}
}
