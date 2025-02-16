package io.saim.dash.account.auth.service;

import io.saim.dash.account.auth.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

	private final SessionManager sessionManager;

	public boolean invalidateSession(String sessionId) {
		if (sessionManager.isValidSession(sessionId)) {
			sessionManager.invalidateSession(sessionId);
			return true;
		}
		return false;
	}
}
