package io.saim.dash.global.exception;

import lombok.Getter;

@Getter
public enum ServiceExceptionContent {

	// 400
	// DEFAULT_BAD_REQUEST(400, "잘못된 요청입니다. 확인 후 다시 시도해주세요."),

	// 401
	// UNAUTHORIZED(401, "인증되지 않았습니다"),
	// AUTHORIZATION_EXPIRED(401, "인증이 만료되었습니다. 잠시 후 다시 시도해주세요"),

	// 403
	// BLOCKED_USER(403, "차단된 사용자입니다. 관리자에게 문의해주세요"),

	// 404
	// DATA_NOT_FOUND(404, "일치하는 데이터ID를 찾을 수 없습니다: %d"),


	// 500
	INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생하였습니다. 잠시 후 다시 시도헤주세요."),

	;

	// FORMAT
	private final int statusCode;
	private String message;

	ServiceExceptionContent(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public ServiceExceptionContent replaceArg(String[] ...args) {
		this.message = String.format(this.message, args);
		return this;
	}
}
