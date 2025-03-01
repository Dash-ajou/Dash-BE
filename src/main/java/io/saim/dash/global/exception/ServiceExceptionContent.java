package io.saim.dash.global.exception;

import lombok.Getter;

@Getter
public enum ServiceExceptionContent {

	// 400

	// 401

	// 404



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
}
