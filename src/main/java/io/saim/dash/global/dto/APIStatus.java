package io.saim.dash.global.dto;

import lombok.Getter;

public enum APIStatus {
	// 200
	SUCCESS(200),
	ACCEPTED(201),

	// 400
	BAD_REQUEST(400),
	UNAUTHORIZED(401),
	FORBIDDEN(403),
	NOT_FOUND(404),

	// 500
	FAILED(500),

	;

	@Getter
	private final Integer statusCode;

	APIStatus(Integer statusCode) {
		this.statusCode = statusCode;
	}
}
